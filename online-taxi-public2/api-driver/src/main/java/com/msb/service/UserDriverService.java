package com.msb.service;

import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.remote.UserDriverClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserDriverService {
    @Autowired
    private UserDriverClient userDriverClient;
    public ResponseResult updateUserDriver(DriverUser driverUser) {
        userDriverClient.updateUser(driverUser);
        return ResponseResult.success("update success");
    }
}
