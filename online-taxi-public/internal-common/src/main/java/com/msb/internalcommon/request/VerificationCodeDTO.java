package com.msb.internalcommon.request;

import lombok.Data;

@Data
public class VerificationCodeDTO {

    //用户手机号
    private String PassengerPhone;

    //用户验证码
    private String verificationCode;


}
