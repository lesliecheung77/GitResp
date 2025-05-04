package com.msb.apipassenger.controller;

import com.msb.apipassenger.service.TokenRefreshService;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    TokenRefreshService tokenRefreshService;
    @PostMapping("/refresh-token")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {
        String tokenRefreshSrc = tokenResponse.getRefreshToken();
        System.out.println("获取原来的tokenRefresh" + tokenRefreshSrc);
        return tokenRefreshService.refreshToken(tokenRefreshSrc);
    }
}
