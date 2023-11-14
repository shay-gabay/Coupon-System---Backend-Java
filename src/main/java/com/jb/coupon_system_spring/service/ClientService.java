package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.repos.CompanyRepository;
import com.jb.coupon_system_spring.repos.CouponRepository;
import com.jb.coupon_system_spring.repos.CustomerRepository;
import com.jb.coupon_system_spring.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class ClientService {
    @Autowired
    protected CompanyRepository companyRepository;
    @Autowired
    protected CustomerRepository customerRepository;
    @Autowired
    protected CouponRepository couponRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    AuthService authService;

    public abstract boolean login(String email, String password) throws Exception;

    public abstract int getIdFromDB(String email);
}
