package com.msb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.dto.DriverCarBindingRelationship;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.mapper.DriverCarBindingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class DriverCarBindingService {
    @Autowired
    private DriverCarBindingMapper driverCarBindingMapper;

    public ResponseResult bindDriverCar(DriverCarBindingRelationship driverCarBindingRelationship) {
        //1.如果司机和车辆已经做过绑定，则不再进行绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        //添加查询条件
        queryWrapper.eq("driverId", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("carId", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("bindState", DriverCarConstants.DRIVER_CAR_BIND);
        Integer DriverAndCar = driverCarBindingMapper.selectCount(queryWrapper);
        if (DriverAndCar.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getValue());
        }
        //2.如果司机已经做过绑定，则不再进行绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapperDriver = new QueryWrapper<>();
        //添加查询条件
        queryWrapperDriver.eq("driverId", driverCarBindingRelationship.getDriverId());
        queryWrapperDriver.eq("bindState", DriverCarConstants.DRIVER_CAR_BIND);
        Integer Driver = driverCarBindingMapper.selectCount(queryWrapperDriver);
        if (Driver.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_BIND_EXISTS.getCode(), CommonStatusEnum.DRIVER_BIND_EXISTS.getValue());
        }

        //3.如果车辆已经做过绑定，则不再进行绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapperCar = new QueryWrapper<>();
        //添加查询条件
        queryWrapperCar.eq("carId", driverCarBindingRelationship.getCarId());
        queryWrapperCar.eq("bindState", DriverCarConstants.DRIVER_CAR_BIND);
        Integer Car = driverCarBindingMapper.selectCount(queryWrapperCar);
        if (Car.intValue() > 0) {
            return ResponseResult.fail(CommonStatusEnum.CAR_BIND_EXISTS.getCode(), CommonStatusEnum.CAR_BIND_EXISTS.getValue());
        }

        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);
        driverCarBindingMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("insert success");
    }

    public ResponseResult unbindDriverCar(DriverCarBindingRelationship driverCarBindingRelationship) {
        HashMap<String, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("driverId", driverCarBindingRelationship.getDriverId());
        objectObjectHashMap.put("carId", driverCarBindingRelationship.getCarId());
        List<DriverCarBindingRelationship> driverCarBindingRelationshipsArray = driverCarBindingMapper.selectByMap(objectObjectHashMap);
        //判断是否存在司机与车辆的绑定关系
        if (driverCarBindingRelationshipsArray.isEmpty()) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(), CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getValue());
        }
        DriverCarBindingRelationship driverCarObject = driverCarBindingRelationshipsArray.get(0);
        LocalDateTime unBindTime = LocalDateTime.now();
        driverCarObject.setBindState(DriverCarConstants.DRIVER_CAR_UNBIND);
        driverCarObject.setUnBindingTime(unBindTime);
        driverCarBindingMapper.updateById(driverCarObject);
        return ResponseResult.success("delete success");
    }
}
