package com.msb.apipassenger.controller;

import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * 没有token进行访问
     * @return
     */
    @GetMapping("/noauth")
    public ResponseResult noauthToke(){
        return ResponseResult.success("noauth test");
    }

    /**
     * 有token进行访问
     * @param
     * @return
     */
    @GetMapping("/auth")
    public ResponseResult authToke(){
        return ResponseResult.success("auth test");
    }
}
