package com.msb.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.msb.internalcommon.constant.TokenTypeConstant;
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
    private static final String JWT_TOKEN_TYPE = "tokenType";
    private static final String JWT_TIME = "time";
    /**
     * 生成token
     * @param passengerPhone
     * @return
     */
    public static String generatorToken(String passengerPhone,String identity,String tokenType){
        Map<String, String> map = new HashMap<>();
        map.put(JWT_PHONE,passengerPhone);
        map.put(JWT_IDENTITY,identity);
        map.put(JWT_TOKEN_TYPE,tokenType);
        //此处的作用是确保通过refreshToken生成的新的双token，因为例如JWT_PHONE，JWT_IDENTITY等都是不变的
        map.put(JWT_TIME,Calendar.getInstance().getTime().toString());
        //整合过期时间，交给redis去管理，此处就不需要了
        //创建builder
        JWTCreator.Builder builder = JWT.create();

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

    /**
     * 校验token，判断token是否异常
     *
     * @param token
     * @return
     */
    public static TokenResult checkTokne(String token){
        TokenResult tokenResult =null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        }catch (Exception e){
            e.printStackTrace();
        }
        return tokenResult;
    }

    @Test
    public void test() {
        String s = generatorToken("17790118534","1", TokenTypeConstant.JWT_TOKEN_ACCESSTOKEN);
        System.out.println("token:" + s);
        System.out.println("解析");
        TokenResult s1 = parseToken(s);
        System.out.println("passengerPhone:" + s1.getPassengerPhone());
        System.out.println("identity:" + s1.getIdentity());
    }
}
