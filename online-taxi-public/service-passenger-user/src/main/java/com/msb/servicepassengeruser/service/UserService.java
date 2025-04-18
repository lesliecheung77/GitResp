package com.msb.servicepassengeruser.service;

import com.msb.internalcommon.dto.PassengerUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    PassengerUserMapper passengerUserMapper;
    public ResponseResult loginorRegister(String passengerPhone) {
        System.out.println("UserService被调用：" + passengerPhone);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("passengerPhone", passengerPhone);
        //通过手机号查询用户
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(hashMap);
        System.out.println(passengerUsers.size() == 0 ? "没有此用户记录" : passengerUsers.get(0).getPassengerPhone());

        return ResponseResult.success(passengerPhone);
    }
}
