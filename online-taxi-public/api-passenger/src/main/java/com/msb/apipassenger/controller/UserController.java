package com.msb.apipassenger.controller;

import com.msb.apipassenger.service.UserService;
import com.msb.internalcommon.dto.ResponseResult;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public ResponseResult getUser(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        System.out.println(accessToken);
        return userService.getUserByAccessToken(accessToken);
    }

}
