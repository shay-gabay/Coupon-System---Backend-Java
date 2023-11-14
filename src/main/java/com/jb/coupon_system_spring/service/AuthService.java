package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.IdName;
import com.jb.coupon_system_spring.beans.Login;
import com.jb.coupon_system_spring.beans.User;
import com.jb.coupon_system_spring.exception.CouponSystemException;

import java.util.UUID;

public interface AuthService {

    void addUser(String email, String password, ClientType clientType);

    void updateUser(int id, String email, String password, ClientType clientType);

    void deleteUser(int userId);

    void register(User user) throws CouponSystemException;

    Login login(User user) throws CouponSystemException;

    void logout(int userId);

    IdName idName(ClientType clientType, String email);
}
