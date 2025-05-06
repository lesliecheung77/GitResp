package com.msb.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class testController {
    @Autowired
    private DriverUserService driverUserService;

    @GetMapping("/test")
    public int test(@PathParam("cityCode") String cityCode) {
        return driverUserService.cityTest(cityCode);
    }
}
