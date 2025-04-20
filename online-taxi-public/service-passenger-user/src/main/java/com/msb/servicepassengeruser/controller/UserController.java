package com.msb.servicepassengeruser.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/user")
    public ResponseResult getUser(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return userService.getUserByPhone(passengerPhone);
    }
}
