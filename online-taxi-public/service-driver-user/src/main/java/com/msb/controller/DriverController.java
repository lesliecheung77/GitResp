package com.msb.controller;

import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DriverController {
    @Autowired
    private DriverUserService driverUserService;
    @PostMapping("/users")
    public ResponseResult addDriver(@RequestBody DriverUser driverUser) {
        return driverUserService.addDriverUser(driverUser);
    }
}
