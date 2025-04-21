package com.msb.internalcommon.dto;

import lombok.Data;

@Data
public class PriceRule {
    private String cityCode;
    private String vehicleType;
    private double startFare;
    private String startMile;
    private Integer unitPricePerMile;
    private double unitPricePerMinute;
}
