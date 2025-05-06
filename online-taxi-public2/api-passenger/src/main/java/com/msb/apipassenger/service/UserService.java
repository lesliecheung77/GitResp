package com.msb.apipassenger.service;

import com.msb.apipassenger.remote.ServicepassengerClient;
import com.msb.internalcommon.dto.PassengerUser;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.dto.TokenResult;
import com.msb.internalcommon.request.VerificationCodeDTO;
import com.msb.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.bouncycastle.crypto.signers.ISOTrailers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    private ServicepassengerClient servicepassengerClient;

    public ResponseResult getUserByAccessToken(String accessToken) {
        //解析accessToken得到手机号
        TokenResult tokenResult = JwtUtils.checkTokne(accessToken);
        String passengerPhone = tokenResult.getPassengerPhone();

        ResponseResult<PassengerUser> userByPhone = servicepassengerClient.getUserByPhone(passengerPhone);

        return ResponseResult.success(userByPhone.getData());
    }
}
