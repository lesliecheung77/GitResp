package com.msb.service;

import com.msb.internalcommon.dto.DriverUserWorkStatus;
import com.msb.internalcommon.dto.ResponseResult;
import com.msb.mapper.DriverUserWorkStatusMapper;
import com.msb.internalcommon.dto.DriverUserWorkStatus;
import com.msb.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 晁鹏飞
 * @since 2022-09-17
 */
@Service
public class DriverUserWorkStatusService {

    @Autowired
    DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult changeWorkStatus(Long driverId, Integer workStatus){

        Map<String,Object> queryMap = new HashMap<>();
        queryMap.put("driverId",driverId);
        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);
        LocalDateTime now = LocalDateTime.now();
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatus.setGmtModified(now);

        driverUserWorkStatus.setWorkStatus(workStatus);

        driverUserWorkStatusMapper.updateById(driverUserWorkStatus);

        return ResponseResult.success("");

    }

//    public ResponseResult<DriverUserWorkStatus> getWorkStatus(Long driverId) {
//        Map<String,Object> queryMap = new HashMap<>();
//        queryMap.put("driver_id",driverId);
//        List<DriverUserWorkStatus> driverUserWorkStatuses = driverUserWorkStatusMapper.selectByMap(queryMap);
//        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatuses.get(0);
//
//        return ResponseResult.success(driverUserWorkStatus);
//    }
}
