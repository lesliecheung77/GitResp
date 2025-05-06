package com.msb.controller;

import com.msb.internalcommon.dto.DriverCar;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping("/addCars")
    public ResponseResult addCar(@RequestBody DriverCar car) {
        return carService.addCar(car);
    }

    @GetMapping("/car")
    public ResponseResult<DriverCar> getCarById(Long carId){

        return carService.getCarById(carId);
    }
}