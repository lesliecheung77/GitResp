package com.msb.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direction")
public class serviceMapController {

    @Autowired
    private MapService mapService;
    @GetMapping("/driving")
    public ResponseResult driving(@RequestBody ForecastPriceDto forecastPriceDto) {
        String depLongitude = forecastPriceDto.getDepLongitude();
        String depLatitude = forecastPriceDto.getDepLatitude();
        String destLongitude = forecastPriceDto.getDestLongitude();
        String destLatitude = forecastPriceDto.getDestLatitude();

        return mapService.driving(depLongitude,depLatitude,destLongitude,destLatitude);
    }
}
