package com.msb.servicevericationcode.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.NumberCodeResponese;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public ResponseResult numberCode(@PathVariable("size") int size) {
        //获取size位随机验证码
        double random = (Math.random()+1) * Math.pow(10,size-1);
        int codeNumber = (int) random;

        //data.put("numberCode", codeNumber);

        //把验证码赋值给NumberCodeResponese对象
        NumberCodeResponese response = new NumberCodeResponese();
        response.setNumberCode(codeNumber);
  

        return ResponseResult.success(response);
    }
}
