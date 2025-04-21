package com.msb.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.DirectionResponse;
import com.msb.internalcommon.responese.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapService {
    public ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        log.info(depLongitude);
        log.info(depLatitude);
        log.info(destLongitude);
        log.info(destLatitude);

        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance("12");
        directionResponse.setDuration("20");

        return ResponseResult.success(directionResponse);
    }
}
