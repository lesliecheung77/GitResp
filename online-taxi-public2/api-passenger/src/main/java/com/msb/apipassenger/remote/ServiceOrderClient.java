package com.msb.apipassenger.remote;

//import com.msb.internalcommon.dto.OrderInfo;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST, value = "/order/add")
    public ResponseResult add(@RequestBody OrderRequest orderRequest);

//    @RequestMapping(method = RequestMethod.GET,value = "/test-real-time-order/{orderId}")
//    public String dispatchRealTimeOrder(@PathVariable("orderId") long orderId);
//
//    @RequestMapping(method = RequestMethod.POST, value = "/order/cancel")
//    public ResponseResult cancel(@RequestParam Long orderId , @RequestParam String identity);
//
//    @RequestMapping(method = RequestMethod.GET, value = "/order/detail")
//    public ResponseResult<OrderInfo> detail(@RequestParam Long orderId);
//
//    @RequestMapping(method = RequestMethod.GET, value = "/order/current")
//    public ResponseResult current(@RequestParam String phone ,@RequestParam String identity);
}
