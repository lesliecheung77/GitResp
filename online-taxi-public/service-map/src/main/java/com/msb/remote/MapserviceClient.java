package com.msb.remote;

import com.msb.internalcommon.constant.MapConfig;
import com.msb.internalcommon.responese.DirectionResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
        stringBuilder.append("output=json");
        stringBuilder.append("&");
        stringBuilder.append("key=" + mapKey);
        //调用高德接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(stringBuilder.toString(),String.class);
        String direction = directionEntity.getBody();

        //解析高德接口
        DirectionResponse directionResponse = parseDirectionEntity(direction);
        return directionResponse;
    }
    private DirectionResponse parseDirectionEntity(String direction){
        DirectionResponse directionResponse = null;
        try {
            //最外层
            JSONObject jsonDirection = JSONObject.fromObject(direction);
            if(jsonDirection.has(MapConfig.STATUS)){
                int status = jsonDirection.getInt(MapConfig.STATUS);
                if(status == 1){//1:正常，0：不正常
                    if(jsonDirection.has(MapConfig.ROUTE)){
                        JSONObject jsonRout = jsonDirection.getJSONObject(MapConfig.ROUTE);
                        JSONArray jsonPathArray = jsonRout.getJSONArray(MapConfig.PATHS);
                        JSONObject jsonObject = jsonPathArray.getJSONObject(0);
                        directionResponse = new DirectionResponse();
                        if(jsonObject.has(MapConfig.DISTANCE)){
                            int distance = jsonObject.getInt(MapConfig.DISTANCE);
                            directionResponse.setDistance(distance);
                        }
                        if(jsonObject.has(MapConfig.DURATION)){
                            int duration = jsonObject.getInt(MapConfig.DURATION);
                            directionResponse.setDuration(duration);
                        }
                    }
                }
            }
        }catch (Exception e){
        }
        return directionResponse;
    }

}
