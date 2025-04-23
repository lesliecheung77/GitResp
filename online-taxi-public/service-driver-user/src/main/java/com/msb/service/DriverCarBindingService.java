package com.msb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.dto.DriverCarBindingRelationship;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.mapper.DriverCarBindingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DriverCarBindingService {
    @Autowired
    private DriverCarBindingMapper driverCarBindingMapper;

    public ResponseResult bindDriverCar(DriverCarBindingRelationship driverCarBindingRelationship){
        //如果司机和车辆已经做过绑定，则不再进行绑定
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        //添加查询条件
        queryWrapper.eq("driverId", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("carId", driverCarBindingRelationship. getCarId());
        queryWrapper.eq("bindState",DriverCarConstants.DRIVER_CAR_BIND);
        Integer i = driverCarBindingMapper.selectCount(queryWrapper);
        if(i.intValue() > 0){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(),CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getValue());
        }


        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindState(DriverCarConstants.DRIVER_CAR_BIND);
        driverCarBindingMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("insert success");
    }
}
