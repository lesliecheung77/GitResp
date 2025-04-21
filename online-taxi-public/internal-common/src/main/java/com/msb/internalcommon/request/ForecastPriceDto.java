package com.msb.internalcommon.request;

import lombok.Data;

@Data
public class ForecastPriceDto {
    //出发地和目的地的经度和纬度
    private String depLongitude;
    private String depLatitude;
    private String destLongitude;
    private String destLatitude;
}
