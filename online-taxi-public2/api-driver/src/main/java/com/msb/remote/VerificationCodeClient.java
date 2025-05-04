package com.msb.remote;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.NumberCodeResponese;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-vericationcode")
public interface VerificationCodeClient {
    @GetMapping("/numberCode/{size}")
    public ResponseResult<NumberCodeResponese> getDriverVerificationCode(@PathVariable("size") int size);
}
