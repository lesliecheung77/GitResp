package com.msb.remote;

import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-driver-user")
public interface UserDriverClient {

    @PostMapping("/updateUsers")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);
}
