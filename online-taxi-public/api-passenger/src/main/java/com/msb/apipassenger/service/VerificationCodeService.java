package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServicepassengerClient;
import com.msb.apipassenger.remote.ServicevericationClient;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.IdentityConstant;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.VerificationCodeDTO;
import com.msb.internalcommon.responese.NumberCodeResponese;
import com.msb.internalcommon.responese.TokenResponse;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import io.netty.util.internal.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class VerificationCodeService {

    @Autowired
    ServicevericationClient servicevericationClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    ServicepassengerClient servicepassengerClient;

    /**
     * 获取验证码
     * @param passengerPhone 用户手机号
     * @return ResponseResult 封装返回信息（code,message,data）
     */
    public ResponseResult generateCode(String passengerPhone) {
        //1.调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        ResponseResult<NumberCodeResponese> numberCodeResponse = servicevericationClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();
        System.out.println("生成的验证码:" + numberCode);

        //2.将验证码存入redis,key,value,ttl(过期时间)
        String key = RedisPrefixUtils.generateKey(passengerPhone);
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        //3.通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联

        return ResponseResult.success(numberCode);
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        //1.根据手机号生成key,并根据Key获取value
        String key = RedisPrefixUtils.generateKey(passengerPhone);
        String valueCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的验证码:" + valueCode);

        //2.校验验证码,如果用户输入的验证码和从redis中取出的验证码不一致或者redis中验证码过期取出的为null，则返回指定的错误信息
        //valueCode == null必须先判断，因为null.trim()会抛异常
        if(valueCode == null || !verificationCode.trim().equals(valueCode.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //3.判断用户是否存在，进行对应注册登录操作
        VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
        verificationCodeDTO.setPassengerPhone(passengerPhone);
        servicepassengerClient.loginOrRegistry(verificationCodeDTO);

        //4.颁发token，不应该使用魔法值，应该使用常量
        String token = JwtUtils.generatorToken(passengerPhone, IdentityConstant.IDENTITY_PASSENGER);

        //5.将生成的token存入redis
        String tokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone,IdentityConstant.IDENTITY_PASSENGER);
        stringRedisTemplate.opsForValue().set(tokenKey,token,30, TimeUnit.DAYS);

        //6.登录成功，响应
        return ResponseResult.success(token);
    }
}
