package com.msb.apipassenger.remote;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.NumberCodeResponese;
import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("service-vericationcode")
public interface ServicevericationClient {
    @RequestMapping(method = RequestMethod.GET,value = "/numberCode/{size}")
    ResponseResult<NumberCodeResponese> getNumberCode(@PathVariable int size);
}
