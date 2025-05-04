package com.msb.controller;

import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.DriverUserExistsResponse;
import com.msb.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DriverController {
    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/users")
    public ResponseResult addDriver(@RequestBody DriverUser driverUser) {
        return driverUserService.addDriverUser(driverUser);
    }

    @PostMapping("/updateUsers")
    public ResponseResult updateDriver(@RequestBody DriverUser driverUser) {
        return driverUserService.updateDriverUser(driverUser);
    }

    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> getDriver(@PathVariable("driverPhone") String driverPhone) {
        ResponseResult<DriverUser> driverUser1 = driverUserService.getDriverUser(driverPhone);
        DriverUser data = driverUser1.getData();
        DriverUserExistsResponse driverUserExistsResponse = new DriverUserExistsResponse();
        int ifExists = DriverCarConstants.DRIVER_EXISTS;
        if(data == null){
            ifExists = DriverCarConstants.DRIVER_NOT_EXISTS;
            driverUserExistsResponse.setDriverPhone(driverPhone);
            driverUserExistsResponse.setIfExists(ifExists);
        }else {
            driverUserExistsResponse.setDriverPhone(data.getDriverPhone());
            driverUserExistsResponse.setIfExists(ifExists);
        }
        return ResponseResult.success(driverUserExistsResponse);
    }
}
