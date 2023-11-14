package com.jb.coupon_system_spring.security;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.User;
import com.jb.coupon_system_spring.exception.CouponSystemException;

import java.util.UUID;

public interface TokenService {
    UUID addToToken(User user);

    void deleteToken(int userId);

    boolean tokenExist(UUID token) throws CouponSystemException;

    boolean isAllowed(UUID token, ClientType clientType) throws CouponSystemException;
}
