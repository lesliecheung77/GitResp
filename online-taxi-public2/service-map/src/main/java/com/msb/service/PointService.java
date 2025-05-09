package com.msb.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.request.PointRequest;
import com.msb.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

    @Autowired
    PointClient pointClient;

    public ResponseResult upload(PointRequest pointRequest){

        return pointClient.upload(pointRequest);
    }
}
