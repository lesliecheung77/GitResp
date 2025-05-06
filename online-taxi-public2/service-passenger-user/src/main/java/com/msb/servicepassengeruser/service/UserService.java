package com.msb.servicepassengeruser.service;

import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.dto.PassengerUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.servicepassengeruser.mapper.PassengerUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {
    @Autowired
    PassengerUserMapper passengerUserMapper;
    public ResponseResult loginorRegister(String passengerPhone) {

        //通过手机号查询用户
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("passengerPhone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(hashMap);

        //判断用户是否存在
        if (passengerUsers.size() == 0) {
            //用户不存在，通过手机号创建用户
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("Mary");
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setPassengerGender((byte)0); //0女 1男
            passengerUser.setState((byte)0);           // 0正常
            LocalDateTime now = LocalDateTime.now();
            passengerUser.setGmtCreate(now);
            passengerUser.setGmtModified(now);
            passengerUserMapper.insert(passengerUser);
        }

        return ResponseResult.success(passengerPhone);
    }

    /**
     * 通过手机号码获取该手机号码的用户信息
     * @param passengerPhone
     * @return
     */
    public ResponseResult getUserByPhone(String passengerPhone) {
        //通过手机号查询用户
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("passengerPhone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(hashMap);
        //判断passengerUsers是否为空
        if(passengerUsers.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXISTS.getCode(),CommonStatusEnum.USER_NOT_EXISTS.getValue());
        }else {
            PassengerUser passengerUser = passengerUsers.get(0);
            return ResponseResult.success(passengerUser);
        }
    }
}
