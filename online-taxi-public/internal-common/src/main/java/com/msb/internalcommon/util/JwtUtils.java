package com.msb.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.msb.internalcommon.dto.TokenResult;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    //盐
    private static final String SIGN = "JFSSJ@$$@%";
    private static final String JWT_PHONE = "passengerPhone";
    private static final String JWT_IDENTITY = "identity"; //1是乘客，2是司机
    /**
     * 生成token
     * @param passengerPhone
     * @return
     */
    public static String generatorToken(String passengerPhone,String identity){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_PHONE,passengerPhone);
        map.put(JWT_IDENTITY,identity);
        //创建builder
        JWTCreator.Builder builder = JWT.create();
        //设置token过期时间并整合过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date time = calendar.getTime();
        //整合过期时间，交给redis去管理，此处就不需要了
        //builder.withExpiresAt(time);

        //整合map
        map.forEach((k, v) -> {builder.withClaim(k,v);});
        //生成token
        String sign = builder.sign(Algorithm.HMAC256(SIGN));
        return sign;
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static TokenResult parseToken(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        String passengerPhone = verify.getClaim(JWT_PHONE).asString();
        String identity = verify.getClaim(JWT_IDENTITY).asString();
        //将passengerPhone和identity封装到Tokenresult对象中返回
        TokenResult tokenResult = new TokenResult();
        tokenResult.setPassengerPhone(passengerPhone);
        tokenResult.setIdentity(identity);
        return tokenResult;
    }

    @Test
    public void test() {
        String s = generatorToken("17790118534","1");
        System.out.println("token:" + s);
        System.out.println("解析");
        TokenResult s1 = parseToken(s);
        System.out.println("passengerPhone:" + s1.getPassengerPhone());
        System.out.println("identity:" + s1.getIdentity());
    }
}
