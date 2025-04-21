package com.msb.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.DirectionResponse;
import com.msb.internalcommon.responese.ForecastPriceResponse;
import com.msb.remote.MapServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ForecastPriceService {

    @Autowired
    private MapServiceClient mapServiceClient;
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude){
        //打印经度和维度
        log.info(depLongitude);
        log.info(depLatitude);
        log.info(destLongitude);
        log.info(destLatitude);
        //封装经度和纬度
        ForecastPriceDto forecastPriceDto = new ForecastPriceDto();
        forecastPriceDto.setDepLongitude(depLongitude);
        forecastPriceDto.setDepLatitude(depLatitude);
        forecastPriceDto.setDestLongitude(destLongitude);
        forecastPriceDto.setDestLatitude(destLatitude);

        ResponseResult<DirectionResponse> direction = mapServiceClient.mapService(forecastPriceDto);
        log.info("距离：" + direction.getData().getDistance() + "米");
        log.info("时间：" + direction.getData().getDistance() / 60 + "分钟");

        ForecastPriceResponse forecastPrice = new ForecastPriceResponse();
        forecastPrice.setPrice(12);
        return ResponseResult.success(forecastPrice);
    }

}
