package com.msb.service;

import com.msb.internalcommon.dto.DriverCar;
import com.msb.remote.UserDriverClient;
import com.msb.remote.ServiceMapClient;
import com.msb.internalcommon.dto.DriverCar;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.ApiDriverPointRequest;
import com.msb.internalcommon.request.PointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private UserDriverClient serviceDriverUserClient;

    public ResponseResult upload(ApiDriverPointRequest apiDriverPointRequest){
        // 获取carId
        Long carId = apiDriverPointRequest.getCarId();

        // 通过carId 获取 tid、trid，调用 service-driver-user的接口
        ResponseResult<DriverCar> carById = serviceDriverUserClient.getCarById(carId);
        DriverCar car = carById.getData();
        String tid = car.getTid();
        String trid = car.getTrid();

        // 调用地图去上传
        PointRequest pointRequest = new PointRequest();
        pointRequest.setTid(tid);
        pointRequest.setTrid(trid);
        pointRequest.setPoints(apiDriverPointRequest.getPoints());

        return serviceMapClient.upload(pointRequest);

    }
}
