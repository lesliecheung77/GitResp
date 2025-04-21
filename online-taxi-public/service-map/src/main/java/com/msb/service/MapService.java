package com.msb.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.DirectionResponse;
import com.msb.internalcommon.responese.ForecastPriceResponse;
import com.msb.remote.MapserviceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MapService {

    @Autowired
    private MapserviceClient mapserviceClient;
    public ResponseResult driving(String depLongitude,String depLatitude,String destLongitude,String destLatitude){

        mapserviceClient.direction(depLongitude,depLatitude,destLongitude,destLatitude);
        
        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance("12");
        directionResponse.setDuration("20");

        return ResponseResult.success(directionResponse);
    }
}
