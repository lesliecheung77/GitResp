package com.msb.service;

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
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingMapper.insert(driverCarBindingRelationship);
        return ResponseResult.success("insert success");
    }
}
