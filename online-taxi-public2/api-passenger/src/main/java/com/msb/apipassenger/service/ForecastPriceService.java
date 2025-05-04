package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServicePriceClient;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServicePriceClient servicePriceClient;
    public ResponseResult forecastPrice(String depLongitude,String depLatitude,String destLongitude,String destLatitude,String cityCode,String vehicleType){

        log.info(depLongitude);
        log.info(depLatitude);
        log.info(destLongitude);
        log.info(destLatitude);

        ForecastPriceDto forecastPriceDto = new ForecastPriceDto();
        forecastPriceDto.setDepLongitude(depLongitude);
        forecastPriceDto.setDepLatitude(depLatitude);
        forecastPriceDto.setDestLongitude(destLongitude);
        forecastPriceDto.setDestLatitude(destLatitude);
        forecastPriceDto.setCityCode(cityCode);
        forecastPriceDto.setVehicleType(vehicleType);

        //调用service-price接口
        ResponseResult<ForecastPriceResponse> forecastPrice1 = servicePriceClient.getForecastPrice(forecastPriceDto);
        ForecastPriceResponse data = forecastPrice1.getData();

        return ResponseResult.success(data);
    }

}
