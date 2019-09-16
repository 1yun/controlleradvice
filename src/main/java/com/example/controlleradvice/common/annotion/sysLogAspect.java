package com.example.controlleradvice.common.annotion;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Aspect
@Component
public class sysLogAspect {

    //定义切入点
    @Pointcut("@annotation(com.example.controlleradvice.common.annotion.sysLog)")
    public void logPointCut(){

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }
    private Object saveSysLog(ProceedingJoinPoint joinPoint, long time) throws Throwable{
        System.out.println("开始执行");
        MethodSignature signature =(MethodSignature) joinPoint.getSignature();

        System.out.println("签名"+signature);
        //拦截的方法名
        Method method=signature.getMethod();
        System.out.println("拦截的方法名"+method);
        sysLog syslog= method.getAnnotation(sysLog.class);
        System.out.println("注解上的描述"+ syslog.value());
        //拦截的类名
        String clazzname=joinPoint.getTarget().getClass().getName();
        System.out.println("拦截的类名"+clazzname);
        String methodName=method.getName();
        System.out.println("方法名"+methodName);
        //请求的参数
        Object[] args= joinPoint.getArgs();
        System.out.println("请求的参数"+args[0]);
        //可以讲日志操作保存到数据表中  保存系统日志
        Object result=joinPoint.proceed();
        System.out.println("结束执行");
        return result;
    }

}
