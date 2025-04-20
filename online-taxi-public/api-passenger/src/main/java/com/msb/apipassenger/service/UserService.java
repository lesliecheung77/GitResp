package com.msb.apipassenger.service;

import com.msb.internalcommon.dto.PassengerUser;
import com.msb.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public ResponseResult getUserByAccessToken(String accessToken) {

        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("张三");
        passengerUser.setProfilePhoto("zhangsan.jpg");

        return ResponseResult.success(passengerUser);
    }
}
