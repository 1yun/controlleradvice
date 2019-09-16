package com.example.controlleradvice.component;

import com.alibaba.fastjson.JSONObject;
import com.example.controlleradvice.common.utils.TokenUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");

        String token = request.getHeader("admin-token");
        if(token != null){
            boolean result = TokenUtil.verify(token);
            if(result){
                System.out.println("通过拦截器");
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;

        try {
           JSONObject json=new JSONObject();
           json.put("success","false");
           json.put("msg","shibai ");
           json.put("code","500");
           response.getWriter().append(json.toJSONString());
        }catch (Exception e){
                e.printStackTrace();
                response.sendError(500);
                return false;
        }
        return false;
    }
}
