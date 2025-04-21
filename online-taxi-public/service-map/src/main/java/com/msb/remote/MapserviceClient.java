package com.msb.remote;

import com.msb.internalcommon.constant.MapConfig;
import com.msb.internalcommon.responese.DirectionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@Service
public class MapserviceClient {
    @Autowired
    private RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(MapserviceClient.class);
    @Value("${amap.key}")
    private String mapKey;

    public DirectionResponse direction(String depLongitude,String depLatitude,String destLongitude,String destLatitude){
        //组装请求调用url
        /**
         * https://restapi.amap.com/v3/direction/driving
         * ?origin=116.481028,39.989643
         * &destination=116.465302,40.004717
         * &extensions=all
         * &output=xml
         * &key=242531ae56c4428e9c2f2c97245cf07b
         */
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MapConfig.DIRECTION_URL);
        stringBuilder.append("?");
        stringBuilder.append("origin=" + depLongitude + "," + depLatitude);
        stringBuilder.append("&");
        stringBuilder.append("destination=" + destLongitude + "," + destLatitude);
        stringBuilder.append("&");
        stringBuilder.append("extensions=base");
        stringBuilder.append("&");
        stringBuilder.append("output=xml");
        stringBuilder.append("&");
        stringBuilder.append("key=" + mapKey);

        log.info(stringBuilder.toString());
        //调用高德接口

        ResponseEntity<String> directionEntity = restTemplate.getForEntity(stringBuilder.toString(),String.class);
        log.info(directionEntity.getBody());
        //解析高德接口
        return null;
    }
}
