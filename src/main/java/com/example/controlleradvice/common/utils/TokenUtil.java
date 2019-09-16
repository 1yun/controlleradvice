package com.example.controlleradvice.common.utils;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.controlleradvice.entity.Student;
import com.auth0.jwt.JWT;

import java.util.Date;

public class TokenUtil {
    private static final long EXPIRE=15*60*1000;
    private static final String SECRET="token123"; //盐一般唯一

    /**
     * 签名生成
     * @param student
     * @return
     */
    public static String sign(Student student){
        String token=null;
        try{
            Date expire=new Date(System.currentTimeMillis()+EXPIRE);
            token=JWT.create().withIssuer("yzy")
                    .withClaim("username",student.getStuName()).withExpiresAt(expire)
                    .sign(Algorithm.HMAC256(SECRET));
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
    public static boolean verify(String token){
        try{
            JWTVerifier verifer = JWT.require(Algorithm.HMAC256(SECRET)).withIssuer("yzy").build();
            DecodedJWT jwt=verifer.verify(token);
            System.out.println("issuer"+jwt.getIssuer());
            System.out.println("username"+jwt.getClaim("username").asString());
            System.out.println("过期时间"+jwt.getExpiresAt());
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
