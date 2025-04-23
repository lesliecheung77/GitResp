package com.msb.controller;

import com.msb.internalcommon.dto.DriverCarBindingRelationship;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.service.DriverCarBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingController {
    @Autowired
    private DriverCarBindingService driverCarBindingService;
    @PostMapping("/bind")
    public ResponseResult bindDriverCar(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship){
        return driverCarBindingService.bindDriverCar(driverCarBindingRelationship);
    }
}
