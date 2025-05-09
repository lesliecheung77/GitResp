package com.msb.internalcommon.util;

/**
 * 生成验证码，token存入redis时的key
 */
public class RedisPrefixUtils {

    //验证码的key前缀
    public static String numberCodeProfix = "VerificationCodeService-code-";

    //token的key前缀
    public static String tokenProfix = "VerificationCodeService-token-";

    // 黑名单设备号
    public static String blackDeviceCodePrefix = "black-device-";

    /**
     * 生成验证码存入redis的key
     * @param passengerPhone
     * @return
     */
    public static String generateKey(String passengerPhone,String identity) {
        return numberCodeProfix + identity + "-" + passengerPhone;
    }

    /**
     * 将token存入redis的key
     * @param passengerPhone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String passengerPhone,String identity,String tokenType) {
        return numberCodeProfix + passengerPhone + "-" + identity + "-" + tokenType;
    }
}
