package com.msb.remote;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("service-map")
public interface MapServiceClient {

    @GetMapping("/direction/driving")
    public ResponseResult<DirectionResponse> mapService(@RequestBody ForecastPriceDto forecastPriceDto);
}
