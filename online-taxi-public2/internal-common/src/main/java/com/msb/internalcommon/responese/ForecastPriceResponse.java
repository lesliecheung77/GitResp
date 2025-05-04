package com.msb.internalcommon.responese;

import lombok.Data;

@Data
public class ForecastPriceResponse {
    private double price;
    private String cityCode;
    private String vehicleType;
    private Integer fare_version;
    private String fare_type;
}
