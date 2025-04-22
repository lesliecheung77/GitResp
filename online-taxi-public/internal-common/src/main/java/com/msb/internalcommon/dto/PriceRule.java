package com.msb.internalcommon.dto;

import lombok.Data;

public class PriceRule {
    private String cityCode;
    private String vehicleType;
    private double startFare;
    private double startMile;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public double getStartFare() {
        return startFare;
    }

    public void setStartFare(double startFare) {
        this.startFare = startFare;
    }

    public double getStartMile() {
        return startMile;
    }

    public void setStartMile(double startMile) {
        this.startMile = startMile;
    }

    public double getUnitPricePerMile() {
        return unitPricePerMile;
    }

    public void setUnitPricePerMile(double unitPricePerMile) {
        this.unitPricePerMile = unitPricePerMile;
    }

    public double getUnitPricePerMinute() {
        return unitPricePerMinute;
    }

    public void setUnitPricePerMinute(double unitPricePerMinute) {
        this.unitPricePerMinute = unitPricePerMinute;
    }

    private double unitPricePerMile;
    private double unitPricePerMinute;
}
