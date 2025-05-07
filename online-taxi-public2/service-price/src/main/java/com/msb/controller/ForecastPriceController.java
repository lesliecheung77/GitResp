package com.msb.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.service.ForecastPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForecastPriceController {
    @Autowired
    ForecastPriceService forecastPriceService;

    /**
     * 计算预估价格
     * @param forecastPriceDto
     * @return
     */
    @PostMapping("/forecast-price")
    public ResponseResult getForecastPrice(@RequestBody ForecastPriceDto forecastPriceDto) {

        String depLongitude = forecastPriceDto.getDepLongitude();
        String depLatitude = forecastPriceDto.getDepLatitude();
        String destLongitude = forecastPriceDto.getDestLongitude();
        String destLatitude = forecastPriceDto.getDestLatitude();
        String cityCode = forecastPriceDto.getCityCode();
        String vehicleType = forecastPriceDto.getVehicleType();

        return forecastPriceService.forecastPrice(depLongitude,depLatitude,destLongitude,destLatitude,cityCode,vehicleType);
    }

    /**
     * 计算实际价格
     * @param distance
     * @param duration
     * @param cityCode
     * @param vehicleType
     * @return
     */
    @PostMapping("/calculate-price")
    public ResponseResult<Double> calculatePrice(@RequestParam Integer distance , @RequestParam Integer duration, @RequestParam String cityCode, @RequestParam String vehicleType){
        return forecastPriceService.calculatePrice(distance,duration,cityCode,vehicleType);
    }
}
