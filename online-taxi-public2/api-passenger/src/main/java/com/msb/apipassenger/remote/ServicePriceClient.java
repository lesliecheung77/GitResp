package com.msb.apipassenger.remote;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-price")
public interface ServicePriceClient {
    @PostMapping("/forecast-price")
    public ResponseResult<ForecastPriceResponse> getForecastPrice(@RequestBody ForecastPriceDto forecastPriceDto);
}
