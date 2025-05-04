package com.msb.controller;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.OrderRequest;
import com.msb.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order")
@Slf4j
@RestController
public class OrderController {

    @Autowired
    private OrderInfoService orderInfoService;

    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest){
        log.info(orderRequest.toString());
        return orderInfoService.add(orderRequest);
    }
}
