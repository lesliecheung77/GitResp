package com.msb.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "JFSSJ@$$@%";
    private static final String JWT_KEY = "passengerPhone";

    /**
     * 生成token
     * @param passengerPhone
     * @return
     */
    public static String generatorToken(String passengerPhone){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY,passengerPhone);
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

    /**
     * 解析Token
     * @param passengerPhone
     * @return
     */
    public static String parseToken(String passengerPhone){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(passengerPhone);
        return verify.getClaim(JWT_KEY).toString();
    }

    @Test
    public void test() {
        String s = generatorToken("123456789");
        System.out.println(s);
        String s1 = parseToken(s);
        System.out.println(s1);
    }
}
