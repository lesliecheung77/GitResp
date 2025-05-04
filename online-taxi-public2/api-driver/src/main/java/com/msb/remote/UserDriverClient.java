package com.msb.remote;

import com.msb.internalcommon.dto.DriverCar;
import com.msb.internalcommon.dto.DriverUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-driver-user")
public interface UserDriverClient {
    //修改司机信息
    @PostMapping("/updateUsers")
    public ResponseResult updateUser(@RequestBody DriverUser driverUser);

    //查询司机是否存在
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> checkDriver(@PathVariable("driverPhone") String driverPhone);

    //获取司机
    @RequestMapping(method = RequestMethod.GET, value = "/car")
    public ResponseResult<DriverCar> getCarById(@RequestParam Long carId);
}
