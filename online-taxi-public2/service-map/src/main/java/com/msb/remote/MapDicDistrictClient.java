package com.msb.remote;

import com.msb.internalcommon.constant.MapConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MapDicDistrictClient {
    //调用高德接口所需要的key
    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    public String dicDistrict(String keywords){

        // &key=<用户的key>
        // 拼装请求的url
        StringBuilder url = new StringBuilder();
        url.append(MapConfig.DISTRICT_URL);
        url.append("?");
        url.append("keywords="+keywords);
        url.append("&");
        url.append("subdistrict=3");
        url.append("&");
        url.append("key="+amapKey);

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);

        //返回响应体
        return forEntity.getBody();
    }
}
