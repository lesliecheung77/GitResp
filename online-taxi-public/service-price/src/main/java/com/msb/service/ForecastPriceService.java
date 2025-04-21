package com.msb.service;

import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.PriceRule;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ForecastPriceDto;
import com.msb.internalcommon.responese.DirectionResponse;
import com.msb.internalcommon.responese.ForecastPriceResponse;
import com.msb.mapper.PriceRuleMapper;
import com.msb.remote.MapServiceClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
public class ForecastPriceService {

    @Autowired
    private MapServiceClient mapServiceClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;
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

        //读取计价规则
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("city_code","110000");
        objectHashMap.put("vehicle_type","1");
        List<PriceRule> priceRules = priceRuleMapper.selectByMap(objectHashMap);
        if(priceRules.size() == 0){
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),CommonStatusEnum.PRICE_RULE_EMPTY.getValue());
        }
        PriceRule priceRule = priceRules.get(0);
        log.info(priceRule.getCityCode());
        log.info(priceRule.getVehicleType());
        //根据距离、时间计算出价格
        ForecastPriceResponse forecastPrice = new ForecastPriceResponse();
        forecastPrice.setPrice(12);
        return ResponseResult.success(forecastPrice);
    }

}
