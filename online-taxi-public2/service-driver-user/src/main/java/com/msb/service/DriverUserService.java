package com.msb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.msb.internalcommon.constant.CommonStatusEnum;
import com.msb.internalcommon.constant.DriverCarConstants;
import com.msb.internalcommon.dto.*;
import com.msb.internalcommon.responese.OrderDriverResponse;
import com.msb.mapper.CarMapper;
import com.msb.mapper.DriverCarBindingMapper;
import com.msb.mapper.DriverUserMapper;
import com.msb.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
public class DriverUserService {
    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    public ResponseResult testDriverUserService() {
        DriverUser driverUser = driverUserMapper.selectById(1);
        return ResponseResult.success(driverUser);
    }

    /**
     * 添加司机信息
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);
        driverUserMapper.insert(driverUser);
        //初始化司机的状态信息
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success(driverUser);
    }

    /**
     * 通过id修改司机信息
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);
        return ResponseResult.success(driverUser);
    }

    /**
     *  通过手机号查询司机信息
     * @param driverPhone
     * @return
     */
    public ResponseResult<DriverUser> getDriverUser(String driverPhone) {
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("driverPhone",driverPhone);
        objectHashMap.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUsers = driverUserMapper.selectByMap(objectHashMap);
        //司机不存在
        if(driverUsers.isEmpty()){
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXITST.getCode(),CommonStatusEnum.DRIVER_NOT_EXITST.getValue());
        }
        DriverUser driverUser = driverUsers.get(0);
        return ResponseResult.success(driverUser);
    }

    @Autowired
    DriverCarBindingMapper driverCarBindingMapper;

    @Autowired
    CarMapper carMapper;

    public ResponseResult<OrderDriverResponse> getAvailableDriver(Long carId){
        // 车辆和司机绑定关系查询
        QueryWrapper<DriverCarBindingRelationship> driverCarBindingRelationshipQueryWrapper = new QueryWrapper<>();
        driverCarBindingRelationshipQueryWrapper.eq("carId",carId);
        driverCarBindingRelationshipQueryWrapper.eq("bindState",DriverCarConstants.DRIVER_CAR_BIND);

        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingMapper.selectOne(driverCarBindingRelationshipQueryWrapper);
        Long driverId = driverCarBindingRelationship.getDriverId();
        // 司机工作状态的查询
        QueryWrapper<DriverUserWorkStatus> driverUserWorkStatusQueryWrapper = new QueryWrapper<>();
        driverUserWorkStatusQueryWrapper.eq("driverId",driverId);
        driverUserWorkStatusQueryWrapper.eq("workStatus",DriverCarConstants.DRIVER_WORK_STATUS_START);

        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(driverUserWorkStatusQueryWrapper);
        if (null == driverUserWorkStatus){
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(),CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getValue());

        }else {
            // 查询司机信息
            QueryWrapper<DriverUser> driverUserQueryWrapper = new QueryWrapper<>();
            driverUserQueryWrapper.eq("id",driverId);
            DriverUser driverUser = driverUserMapper.selectOne(driverUserQueryWrapper);
            // 查询车辆信息
            QueryWrapper<DriverCar> carQueryWrapper = new QueryWrapper<>();
            carQueryWrapper.eq("id",carId);
            DriverCar car = carMapper.selectOne(carQueryWrapper);


            OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
            orderDriverResponse.setCarId(carId);
            orderDriverResponse.setDriverId(driverId);
            orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());

            orderDriverResponse.setLicenseId(driverUser.getLicenseId());
            orderDriverResponse.setVehicleNo(car.getVehicleNo());
            orderDriverResponse.setVehicleType(car.getVehicleType());

            return ResponseResult.success(orderDriverResponse);
        }
    }

    /**
     * 测试
     * @param cityCode
     * @return
     */
    public int cityTest(String cityCode){
        return driverUserMapper.selectDriverUserCountByCityCode(cityCode);
    }
}
