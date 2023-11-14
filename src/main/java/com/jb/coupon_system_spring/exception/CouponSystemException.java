package com.jb.coupon_system_spring.exception;

public class CouponSystemException extends Exception {
    public CouponSystemException(ErrMsg errMsg) {
        super(errMsg.getMessage());
    }
}
