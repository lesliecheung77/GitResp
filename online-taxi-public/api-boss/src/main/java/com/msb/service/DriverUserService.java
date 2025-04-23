package com.msb.service;

import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.remote.DriverUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DriverUserService {
    @Autowired
    private DriverUserClient driverUserClient;

    public ResponseResult<DriverUser> addDriverUser(DriverUser driverUser) {
        driverUserClient.addDriverUser(driverUser);
        return ResponseResult.success("Driver User added successfully");
    }

    public ResponseResult<DriverUser> updateDriverUser(DriverUser driverUser) {
        driverUserClient.updateDriverUser(driverUser);
        return ResponseResult.success("Driver User updated successfully");
    }
}
