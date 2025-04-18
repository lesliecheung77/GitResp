package com.msb.apipassenger.controller;

import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    /**
     * 没有token进行访问
     * @return
     */
    @GetMapping("/noauth")
    public ResponseResult noauthToke(){
        return ResponseResult.success();
    }

    /**
     * 有token进行访问
     * @param token
     * @return
     */
    @GetMapping("/auth")
    public ResponseResult authToke(String token){
        return ResponseResult.success(token);
    }
}
