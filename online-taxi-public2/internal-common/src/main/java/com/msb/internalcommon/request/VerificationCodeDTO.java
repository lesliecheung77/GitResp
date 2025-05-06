package com.msb.internalcommon.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {

    //用户手机号
    private String passengerPhone;

    //用户验证码
    private String verificationCode;

    //司机手机号
    private String driverPhone;
}
