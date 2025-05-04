package com.msb.service;

import com.msb.internalcommon.dto.DriverCar;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.TerminalResponse;
import com.msb.internalcommon.responese.TrackResponse;
import com.msb.mapper.CarMapper;
import com.msb.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CarService {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult addCar(DriverCar car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);
        //插入tid，车辆在高德地图上的终端
        String vehicleNo = car.getVehicleNo();
        Long id = car.getId();
        ResponseResult<TerminalResponse> terminalResponseResponseResult = serviceMapClient.addTerminal(vehicleNo, id+"");

        //调用高德API获取tid并设置tid到Car中
        String tid = terminalResponseResponseResult.getData().getTid();
        car.setTid(tid);

        //插入trid
        ResponseResult<TrackResponse> trackResponseResponseResult = serviceMapClient.addTrack(tid);
        String trid = trackResponseResponseResult.getData().getTrid();
        String trname = trackResponseResponseResult.getData().getTrname();
        car.setTrid(trid);
        car.setTrname(trname);

        carMapper.updateById(car);

        return ResponseResult.success(car);
    }

    public ResponseResult<DriverCar> getCarById(Long id){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);

        List<DriverCar> cars = carMapper.selectByMap(map);

        return ResponseResult.success(cars.get(0));

    }
}
