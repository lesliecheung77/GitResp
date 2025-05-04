package com.msb.controller;

import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.PriceRule;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.service.UserDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserDriverService userDriverService;
    @PostMapping("/userDriver")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
        return userDriverService.updateUserDriver(driverUser);
    }
}
