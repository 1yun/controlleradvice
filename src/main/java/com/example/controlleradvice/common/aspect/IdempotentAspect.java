package com.example.controlleradvice.common.aspect;

import com.alibaba.fastjson.JSON;
import com.example.controlleradvice.common.annotion.Exception.CommonException;
import com.example.controlleradvice.common.annotion.Idempotent;
import com.example.controlleradvice.common.service.ICacheService;
import com.example.controlleradvice.common.utils.IdempotentFlag;
import com.example.controlleradvice.common.utils.MD5Utils;
import com.example.controlleradvice.common.utils.RedisLockUtil;
import com.example.controlleradvice.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@Aspect
@Order(-1)
@Slf4j
public class IdempotentAspect {

    @Autowired
    private ICacheService cacheService;

    /**
     * 定义切入点为 带有 Idempotent 注解的
     */
    @Pointcut("@annotation(com.example.controlleradvice.common.annotion.Idempotent)")
    public void idempotent() {
    }

    @Around("idempotent()")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        Object result = null;
        // joinPoint获取参数名
        String[] params = ((CodeSignature) joinPoint.getStaticPart().getSignature()).getParameterNames();
        // joinPoint获取参数值
        Object[] args = joinPoint.getArgs();
        // IdempotentFlag利用ThreadLocal是否正在进行中
        if (!IdempotentFlag.isDoing()) {
            try {
                IdempotentFlag.doing();
                Map<String, Object> map = new HashMap<String, Object>();
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        map.put(params[i], args[i]);
                        System.out.println(params[i]+"---"+args[i]);
                    }
                }
                Signature signature = joinPoint.getSignature();
                MethodSignature methodSignature = (MethodSignature) signature;
                Method method = methodSignature.getMethod();
                Idempotent idempotent = method.getAnnotation(Idempotent.class);
                // 类似springcache的写法，解析出express的实际值作为key
                //String idemKey = MD5Utils.hexdigest(JSON.toJSONString(map));
                String idemKey = getIdempotentKey(map, idempotent.express());
                System.out.println(idemKey);

                if (StringUtils.isEmpty(idempotent.value()) || StringUtils.isEmpty(idemKey)) {
                    throw CommonException.FAILURE("请稍后再试");
                } else {
                    // 利用redisLock加锁获取结果
                    result = getResult(joinPoint, idempotent.value(), idemKey);
                }
            } catch (Throwable e) {
                throw CommonException.FAILURE(e.getMessage());
            } finally {
                IdempotentFlag.done();
            }
        } else {
            try {
                result = joinPoint.proceed(args);
            } catch (Throwable e) {
                throw CommonException.FAILURE("请稍后再试");
            }
        }
        return result;
    }

    public Object getResult(ProceedingJoinPoint joinPoint, String idemGroup, String idemKey) throws Throwable {
        cacheService.get("idmpotent:value:" + idemGroup + ":" + idemKey);
        Object result;
        try {
            RedisLockUtil.lock("idmpotent:lock:" + idemGroup + ":" + idemKey, SECONDS, 60);
            result = cacheService.get("idmpotent:value:" + idemGroup + ":" + idemKey);
            if (result != null) {
                return result;
            }
            // 需要加锁的代码
            result = joinPoint.proceed(joinPoint.getArgs());
            if (result != null) {
                // 结果缓存1分钟
                cacheService.add("idmpotent:value:" + idemGroup + ":" + idemKey, result, 60);
            }
        } catch (Throwable e) {
            throw CommonException.FAILURE(e.getMessage());
        } finally {
            // 释放锁
            RedisLockUtil.unlock("idmpotent:lock:" + idemGroup + ":" + idemKey);
        }
        return result;
    }

    private String getIdempotentKey(Map<String,Object> map,String express){
        Student s=(Student) map.get("student");
        return s.getId()+"-"+s.getAge();
    }

}