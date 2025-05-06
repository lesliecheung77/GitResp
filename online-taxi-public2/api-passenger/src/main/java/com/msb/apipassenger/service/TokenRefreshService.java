package com.msb.apipassenger.service;

import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.TokenTypeConstant;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.dto.TokenResult;
import com.msb.internalcommon.responese.TokenResponse;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenRefreshService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    public ResponseResult refreshToken(String refreshTokenSrc) {
        //1.解析前端传入的refreshTokenSrc
        TokenResult tokenResult = JwtUtils.checkTokne(refreshTokenSrc);
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }
        String passengerPhone = tokenResult.getPassengerPhone();
        String identity = tokenResult.getIdentity();

        //2.从redis中获取refreshToken
        String RedisRefreshTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, identity, TokenTypeConstant.JWT_TOKEN_REFRESHTOKNE);
        String RedisRefreshToken = stringRedisTemplate.opsForValue().get(RedisRefreshTokenKey);

        //3.校验，对比refreshTokenSrc和refreshToken
        if((StringUtils.isBlank(RedisRefreshToken)) || (!refreshTokenSrc.trim().equals(RedisRefreshToken.trim()))){
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),CommonStatusEnum.TOKEN_ERROR.getValue());
        }

        //4.校验成功，生成新的双token(accessTokne和refreshToken)
        String accessToken = JwtUtils.generatorToken(passengerPhone, identity, TokenTypeConstant.JWT_TOKEN_ACCESSTOKEN);
        String refreshToken = JwtUtils.generatorToken(passengerPhone, identity, TokenTypeConstant.JWT_TOKEN_REFRESHTOKNE);

        String RedisAccessTokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, identity, TokenTypeConstant.JWT_TOKEN_ACCESSTOKEN);


        //4.1将双token以及对应的tokenKey存入Redis
        stringRedisTemplate.opsForValue().set(RedisAccessTokenKey,accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(RedisRefreshTokenKey,refreshToken,30, TimeUnit.DAYS);

        //4.2封装token
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }
}
