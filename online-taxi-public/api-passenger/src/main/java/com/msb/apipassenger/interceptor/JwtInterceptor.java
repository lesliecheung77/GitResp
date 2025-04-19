package com.msb.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.dto.TokenResult;
import com.msb.internalcommon.util.JwtUtils;
import com.msb.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.msb.internalcommon.constant.TokenTypeConstant

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        //定义错误提示信息和状态标识
        String errorMessage = "default error message!";
        boolean result = true;
        TokenResult tokenResult = new TokenResult();
        
        try {
            //解析token,得到手机号和身份标识
            tokenResult = JwtUtils.parseToken(token);
        }catch (SignatureVerificationException e){
            errorMessage = "token sign error";
            result = false;
        }catch (TokenExpiredException e){
            errorMessage = "token time out";
            result = false;
        }catch (AlgorithmMismatchException e){
            errorMessage = "AlgorithmMismatchException error";
            result = false;
        }catch (Exception e){
            errorMessage = "token invalid";
            result = false;
        }

        if(tokenResult == null){
            errorMessage = "token invalid";
            result = false;
        }else {
            //从redis中取得token，首先需要获得tokenKey
            String passengerPhone = tokenResult.getPassengerPhone();
            String identity = tokenResult.getIdentity();
            String tokenKey = RedisPrefixUtils.generateTokenKey(passengerPhone, identity,TokenTypeConstant.JWT_TOKEN_ACCESSTOKEN);
            String tokenForRedis = stringRedisTemplate.opsForValue().get(tokenKey);

            //判断tokenForRedis是否为空
            if(StringUtils.isBlank(tokenForRedis)){
                errorMessage = "token invalid";
                result = false;
            }else {
                if(!token.trim().equals(tokenForRedis.trim())){
                    errorMessage = "token invalid";
                    result = false;
                }
            }
        }


        //将前端请求中传入的token和从redis取得的token比较
        if (!result){
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(errorMessage)).toString());
        }

        return result;
    }
}
