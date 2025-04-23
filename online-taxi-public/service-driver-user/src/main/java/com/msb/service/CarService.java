package com.msb.service;

import com.msb.internalcommon.dto.DriverCar;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {
    @Autowired
    private CarMapper carMapper;

    public ResponseResult addCar(DriverCar car) {
        carMapper.insert(car);
        return ResponseResult.success("add car success");
    }
}
