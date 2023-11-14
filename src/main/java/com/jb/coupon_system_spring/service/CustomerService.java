package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.exception.CouponSystemException;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    void purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException;

    Optional<Coupon> getSingleCoupon(int customerId, int couponId) throws CouponSystemException;

    Optional<Coupon> getSingleCoupon(int couponId) throws CouponSystemException;

    List<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException;

    List<Coupon> getCustomerCouponsNotPurchased(int customerId) throws CouponSystemException;

    List<Coupon> getCustomerCoupons(int customerId, Category category) throws CouponSystemException;

    List<Coupon> getCustomerCoupons(int customerId, double maxPrice) throws CouponSystemException;

    Customer getCustomerDetails(int customerId) throws CouponSystemException;

}
