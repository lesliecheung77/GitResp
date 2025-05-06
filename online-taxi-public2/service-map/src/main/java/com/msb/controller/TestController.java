package com.msb.controller;

import com.msb.internalcommon.dto.DicDistrict;
import com.msb.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    private DicDistrictMapper dicDistrictMapper;
    @GetMapping("/test")
    public String driving(){
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("addressCode",11000);
        List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(objectObjectHashMap);
        DicDistrict dicDistrict = dicDistricts.get(0);
        System.out.println(dicDistrict.getAddressName());
        return "driving";
    }
}
