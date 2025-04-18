package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServicevericationClient;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.NumberCodeResponese;
import com.msb.internalcommon.responese.TokenResponse;
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

    private String numberCodeProfix = "VerificationCodeService-code-";

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

        //2.存入redis,key,value,ttl(过期时间)
        String key = generateKey(passengerPhone);
        stringRedisTemplate.opsForValue().set(key,numberCode+"",2, TimeUnit.MINUTES);
        //3.通过短信服务商，将对应的验证码发送到手机上。阿里短信服务，腾讯短信通，华信，容联

        return ResponseResult.success(numberCode);
    }

    /**
     * 对key进行封装，在将验证码存入redis和从redis取出验证码时调用此方法
     * @param passengerPhone
     * @return
     */
    public String generateKey(String passengerPhone) {
        return numberCodeProfix + passengerPhone;
    }

    /**
     * 校验验证码
     * @param passengerPhone 手机号
     * @param verificationCode 验证码
     * @return
     */
    public ResponseResult checkCode(String passengerPhone, String verificationCode) {
        //1.根据手机号生成key,并根据Key获取value
        String key = generateKey(passengerPhone);
        String valueCode = stringRedisTemplate.opsForValue().get(key);
        System.out.println("redis中的验证码:" + valueCode);

        //2.校验验证码,如果用户输入的验证码和从redis中取出的验证码不一致或者redis中验证码过期取出的为null，则返回指定的错误信息
        //valueCode == null必须先判断，因为null.trim()会抛异常
        if(valueCode == null || !verificationCode.trim().equals(valueCode.trim())) {
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }
        //3.判断用户是否存在，进行对应注册登录操作

        //4.登录成功，颁发token

        //5.响应
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");
        return ResponseResult.success(tokenResponse);
    }
}
