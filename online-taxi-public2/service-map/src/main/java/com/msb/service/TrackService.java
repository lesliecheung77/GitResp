package com.msb.service;

import com.msb.internalcommon.dto.ResponseResult;
import com.msb.internalcommon.responese.TrackResponse;
import com.msb.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrackService {

    @Autowired
    TrackClient trackClient;

    public ResponseResult<TrackResponse> add(String tid){

        return trackClient.add(tid);
    }
}
