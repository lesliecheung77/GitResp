package com.msb.servicepassengeruser.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.msb.internalcommon.request.VerificationCodeDTO;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/service-passenger-user")
    public ResponseResult loginorRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("controller层收到的手机号：" + passengerPhone);
        return userService.loginorRegister(passengerPhone);
    }

    @GetMapping("/user/{phone}")
    public ResponseResult getUser(@PathVariable("phone") String passengerPhone) {
        return userService.getUserByPhone(passengerPhone);
    }
}
