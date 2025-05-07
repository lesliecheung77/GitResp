package com.msb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.PriceRule;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.DirectionResponse;
import com.msb.internalcommon.responese.ForecastPriceResponse;
import com.msb.internalcommon.util.BigDecimalUtils;
import com.msb.mapper.PriceRuleMapper;
import com.msb.remote.MapServiceClient;
import feign.QueryMap;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.cms.bc.BcKEKRecipientInfoGenerator;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ForecastPriceService {

    @Autowired
    private MapServiceClient mapServiceClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;
    public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude,String cityCode,String vehicleType){
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
        Integer distance = direction.getData().getDistance();
        Integer duration = direction.getData().getDistance();
        log.info("距离：" + distance + "米");
        log.info("时间：" + duration / 60 + "分钟");

        //读取计价规则
//        HashMap<String, Object> objectHashMap = new HashMap<>();
//        objectHashMap.put("city_code","110000");
//        objectHashMap.put("vehicle_type","1");
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if(priceRules.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);
        log.info(priceRule.getCityCode());
        log.info(priceRule.getVehicleType());
        //根据距离、时间和计价规则计算出预估价格
        double price = getPrice(distance, duration, priceRule);

        ForecastPriceResponse forecastPrice = new ForecastPriceResponse();
        forecastPrice.setPrice(price);
        forecastPrice.setCityCode(cityCode);
        forecastPrice.setVehicleType(vehicleType);
        forecastPrice.setFare_version(priceRule.getFareVersion());
        forecastPrice.setFare_type(priceRule.getFareType());
        return ResponseResult.success(forecastPrice);
    }

    /**
     * 计算实际价格
     * @param distance
     * @param duration
     * @param cityCode
     * @param vehicleType
     * @return
     */
    public ResponseResult<Double> calculatePrice( Integer distance ,  Integer duration, String cityCode, String vehicleType){
        // 查询计价规则
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("city_code",cityCode);
        queryWrapper.eq("vehicle_type",vehicleType);
        queryWrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);
        if (priceRules.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.VERIFICATION_CODE_ERROR.getValue());
        }

        PriceRule priceRule = priceRules.get(0);

        log.info("根据距离、时长和计价规则，计算价格");

        double price = getPrice(distance, duration, priceRule);
        return ResponseResult.success(price);
    }

    /**
     *根据这三个参数计算预估价格
     * @param distance  实际距离
     * @param duration  实际时长
     * @param priceRule 计价规则
     * @return
     */
    private static double getPrice(Integer distance, Integer duration,PriceRule priceRule){
        //初始化预估价格+起步价
        double price = 0;
        double startFare = priceRule.getStartFare();
        price = BigDecimalUtils.add(price, startFare);

        //获取实际公里数和起步公里数，并计算出收费里程数
        double distanceBigdecimal = BigDecimalUtils.divide(distance, 1000);
        double startMileBigDecimal = priceRule.getStartMile();

        //实际公里数-起始公里数
        Double moreMile = BigDecimalUtils.substract(distanceBigdecimal, startMileBigDecimal);
        Double actualMile = moreMile < 0 ? 0 : moreMile;  //如果实际里程没有超过起步价不算入里程数

        //获取收费里程数每公里价格,计算超出起始里程的价格
        Double PricePerMile = priceRule.getUnitPricePerMile();
        Double mileFare = BigDecimalUtils.multiply(actualMile, PricePerMile);
        price = BigDecimalUtils.add(price,mileFare);

        //获取总时长
        double durationBigDecimal = BigDecimalUtils.divide(duration,60);
        //获取时长单价
        double unitPricePerMinuteDouble = priceRule.getUnitPricePerMinute();
        //计算总时长价格
        double durationPrice = BigDecimalUtils.multiply(durationBigDecimal,unitPricePerMinuteDouble);
        price = BigDecimalUtils.add(price,durationPrice);

        return price;
    }
    @Test
    public void forecastPriceTest(){
        PriceRule priceRule = new PriceRule();
        priceRule.setStartMile(3);
        priceRule.setStartFare(10.0);
        priceRule.setUnitPricePerMile(1.8);
        priceRule.setUnitPricePerMinute(0.5);
        double price = getPrice(6500, 1800, priceRule);
        System.out.println(price);
    }
}
