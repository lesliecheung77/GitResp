package com.msb.remote;

import com.msb.internalcommon.dto.DriverCar;
import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-driver-user")
public interface DriverUserClient {
    @PostMapping("/users")
    public ResponseResult<DriverUser> addDriverUser(@RequestBody DriverUser driverUser);

    @PostMapping("/updateUsers")
    public ResponseResult<DriverUser> updateDriverUser(@RequestBody DriverUser driverUser);

    @PostMapping("/addCars")
    public ResponseResult<DriverCar> addCar(@RequestBody DriverCar driverCar);
}
