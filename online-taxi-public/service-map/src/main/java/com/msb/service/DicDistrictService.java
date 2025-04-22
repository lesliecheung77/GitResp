package com.msb.service;

import com.msb.internalcommon.constant.MapConfig;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.DicDistrict;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.mapper.DicDistrictMapper;
import com.msb.remote.MapDicDistrictClient;
import com.msb.remote.MapserviceClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DicDistrictService {

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @Autowired
    private MapDicDistrictClient mapDicDistrictClient;

    public ResponseResult initDicDistrict(String keywords) {

        // 请求地图
        String dicDistrictResult = mapDicDistrictClient.dicDistrict(keywords);
        System.out.println(dicDistrictResult);
        // 解析结果
        JSONObject dicDistrictJsonObject = JSONObject.fromObject(dicDistrictResult);
        int status = dicDistrictJsonObject.getInt(MapConfig.STATUS);
        if (status != 1) {
            return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_ERROR.getCode(), CommonStatusEnum.MAP_DISTRICT_ERROR.getValue());
        }
        JSONArray countryJsonArray = dicDistrictJsonObject.getJSONArray(MapConfig.DISTRICTS);
        for (int country = 0; country < countryJsonArray.size(); country++) {
            JSONObject countryJsonObject = countryJsonArray.getJSONObject(country);
            String countryAddressCode = countryJsonObject.getString(MapConfig.ADCODE);
            String countryAddressName = countryJsonObject.getString(MapConfig.NAME);
            String countryParentAddressCode = "0";
            String countryLevel = countryJsonObject.getString(MapConfig.LEVEL);

            insertDicDistrict(countryAddressCode, countryAddressName, countryLevel, countryParentAddressCode);

            JSONArray proviceJsonArray = countryJsonObject.getJSONArray(MapConfig.DISTRICTS);
            for (int p = 0; p < proviceJsonArray.size(); p++) {
                JSONObject proviceJsonObject = proviceJsonArray.getJSONObject(p);
                String proviceAddressCode = proviceJsonObject.getString(MapConfig.ADCODE);
                String proviceAddressName = proviceJsonObject.getString(MapConfig.NAME);
                String proviceParentAddressCode = countryAddressCode;
                String proviceLevel = proviceJsonObject.getString(MapConfig.LEVEL);

                insertDicDistrict(proviceAddressCode, proviceAddressName, proviceLevel, proviceParentAddressCode);

                JSONArray cityArray = proviceJsonObject.getJSONArray(MapConfig.DISTRICTS);
                for (int city = 0; city < cityArray.size(); city++) {
                    JSONObject cityJsonObject = cityArray.getJSONObject(city);
                    String cityAddressCode = cityJsonObject.getString(MapConfig.ADCODE);
                    String cityAddressName = cityJsonObject.getString(MapConfig.NAME);
                    String cityParentAddressCode = proviceAddressCode;
                    String cityLevel = cityJsonObject.getString(MapConfig.LEVEL);

                    insertDicDistrict(cityAddressCode, cityAddressName, cityLevel, cityParentAddressCode);

                    JSONArray districtArray = cityJsonObject.getJSONArray(MapConfig.DISTRICTS);
                    for (int d = 0; d < districtArray.size(); d++) {
                        JSONObject districtJsonObject = districtArray.getJSONObject(d);
                        String districtAddressCode = districtJsonObject.getString(MapConfig.ADCODE);
                        String districtAddressName = districtJsonObject.getString(MapConfig.NAME);
                        String districtParentAddressCode = cityAddressCode;
                        String districtLevel = districtJsonObject.getString(MapConfig.LEVEL);

                        if (districtLevel.equals(MapConfig.STREET)) {
                            continue;
                        }

                        insertDicDistrict(districtAddressCode, districtAddressName, districtLevel, districtParentAddressCode);

                    }
                }
            }

        }


        return ResponseResult.success("");
    }

    public void insertDicDistrict(String addressCode, String addressName, String level, String parentAddressCode) {
        // 数据库对象
        DicDistrict district = new DicDistrict();
        district.setAddressCode(addressCode);
        district.setAddressName(addressName);
        int levelInt = generateLevel(level);
        district.setLevel(levelInt);

        district.setParentAddressCode(parentAddressCode);

        // 插入数据库
        dicDistrictMapper.insert(district);
    }

    public int generateLevel(String level) {
        int levelInt = 0;
        if (level.trim().equals("country")) {
            levelInt = 0;
        } else if (level.trim().equals("province")) {
            levelInt = 1;
        } else if (level.trim().equals("city")) {
            levelInt = 2;
        } else if (level.trim().equals("district")) {
            levelInt = 3;
        }
        return levelInt;
    }
}
