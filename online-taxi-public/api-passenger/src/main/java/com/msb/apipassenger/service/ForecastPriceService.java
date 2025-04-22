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
    public ResponseResult forecastPrice(String depLongitude,String depLatitude,String destLongitude,String destLatitude){

        log.info(depLongitude);
        log.info(depLatitude);
        log.info(destLongitude);
        log.info(destLatitude);

        ForecastPriceDto forecastPriceDto = new ForecastPriceDto();
        forecastPriceDto.setDepLongitude(depLongitude);
        forecastPriceDto.setDepLatitude(depLatitude);
        forecastPriceDto.setDestLongitude(destLongitude);
        forecastPriceDto.setDestLatitude(destLatitude);

        //调用service-price接口
        ResponseResult<ForecastPriceResponse> forecastPrice1 = servicePriceClient.getForecastPrice(forecastPriceDto);
        double price = forecastPrice1.getData().getPrice();

        ForecastPriceResponse forecastPrice = new ForecastPriceResponse();
        forecastPrice.setPrice(price);
        return ResponseResult.success(forecastPrice);
    }

}
