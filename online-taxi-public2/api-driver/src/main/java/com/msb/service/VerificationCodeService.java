package com.msb.service;

import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.constant.TokenTypeConstant;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.DriverUserExistsResponse;
import com.msb.internalcommon.responese.NumberCodeResponese;
import com.msb.internalcommon.responese.TokenResponse;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import com.msb.remote.UserDriverClient;
import com.msb.remote.VerificationCodeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class VerificationCodeService {

    @Autowired
    private UserDriverClient userDriverClient;
    @Autowired
    private VerificationCodeClient verificationCodeClient;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public ResponseResult checkAndSendCode(String driverPhone){
        //检查司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserResponseResult = userDriverClient.checkDriver(driverPhone);
        DriverUserExistsResponse data = driverUserResponseResult.getData();
        int ifExists = data.getIfExists();
        if(ifExists == DriverCarConstants.DRIVER_NOT_EXISTS){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXITST.getCode(),CommonStatusEnum.DRIVER_NOT_EXITST.getValue());
        }
        log.info("司机存在");
        //获取验证码
        ResponseResult<NumberCodeResponese> driverVerificationCode = verificationCodeClient.getDriverVerificationCode(6);
        NumberCodeResponese DriverVerificationCodeData = driverVerificationCode.getData();
        int numberCode = DriverVerificationCodeData.getNumberCode();
        log.info("验证码：" + numberCode);

        //调用第三方接口发送验证码,后续再做

        //存入redis，生成对应的key
        String key = RedisPrefixUtils.generateKey(driverPhone, IdentityConstant.IDENTITY_DRIVER);
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        return ResponseResult.success("");
    }

    /**
     * 校验验证码
     * @param driverPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String driverPhone, String verificationCode) {
        //1.根据手机号生成key,并根据Key获取value
        String key = RedisPrefixUtils.generateKey(driverPhone,IdentityConstant.IDENTITY_DRIVER);
        String valueCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的验证码:" + valueCode);

        //2.校验验证码,如果用户输入的验证码和从redis中取出的验证码不一致或者redis中验证码过期取出的为null，则返回指定的错误信息
        //valueCode == null必须先判断，因为null.trim()会抛异常
        if(valueCode == null || !verificationCode.trim().equals(valueCode.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //4.颁发token，不应该使用魔法值，应该使用常量。两个token，一个通行accessToken,过期了通过refreshToken获得新的accessToken
        String accessToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.IDENTITY_DRIVER, TokenTypeConstant.JWT_TOKEN_ACCESSTOKEN);
        String refreshToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.IDENTITY_DRIVER, TokenTypeConstant.JWT_TOKEN_REFRESHTOKNE);

        //5.将生成的accessToken、refreshToken以及它们对应的key存入redis
        String tokenAccessKey = RedisPrefixUtils.generateTokenKey(driverPhone,IdentityConstant.IDENTITY_DRIVER,TokenTypeConstant.JWT_TOKEN_ACCESSTOKEN);
        String tokenRefreshKey = RedisPrefixUtils.generateTokenKey(driverPhone,IdentityConstant.IDENTITY_DRIVER,TokenTypeConstant.JWT_TOKEN_REFRESHTOKNE);

        stringRedisTemplate.opsForValue().set(tokenAccessKey,accessToken,30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(tokenRefreshKey,refreshToken,31, TimeUnit.DAYS);

        //6.登录成功，响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);
        return ResponseResult.success(tokenResponse);
    }
}
