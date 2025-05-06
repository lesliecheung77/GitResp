package com.msb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.msb.internalcommon.dto.DriverCar;
import org.springframework.stereotype.Repository;

@Repository
public interface CarMapper extends BaseMapper<DriverCar> {
}
