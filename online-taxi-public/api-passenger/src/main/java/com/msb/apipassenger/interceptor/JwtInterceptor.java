package com.msb.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.dto.TokenResult;
import com.msb.internalcommon.util.JwtUtils;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.security.SignatureException;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorazition");

        //定义错误提示信息和状态标识
        String errorMessage = "default error message!";
        boolean result = true;

        try {
            //解析token
            JwtUtils.parseToken(token);
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

        if (!result){
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(errorMessage)).toString());
        }

        return result;
    }
}
