package com.msb.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "JFSSJ@$$@%";

    //生成token
    public static String generatorToken(Map<String, String> map){
        //创建builder
        JWTCreator.Builder builder = JWT.create();
        //设置token过期时间并整合过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date time = calendar.getTime();
        builder.withExpiresAt(time);

        //整合map
        map.forEach((k, v) -> {builder.withClaim(k,v);});
        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    public static void main(String[] args) {
        Map<String, String> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("id", "1");
        objectObjectHashMap.put("name","张三");
        String s = generatorToken(objectObjectHashMap);
        System.out.println(s);

    }
}
