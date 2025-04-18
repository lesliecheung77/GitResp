package com.msb.apipassenger.remote;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("service-passenger-user")
public interface ServicepassengerClient {

    @PostMapping("/service-passenger-user")
    public ResponseResult loginOrRegistry(@RequestBody VerificationCodeDTO verificationCodeDTO);
}
