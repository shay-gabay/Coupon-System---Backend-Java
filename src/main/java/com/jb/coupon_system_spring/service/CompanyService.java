package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    void addCoupon(int companyId, Coupon coupon) throws CouponSystemException;

    void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException;

    void updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException;

    void deleteCoupon(int couponId) throws CouponSystemException;

    void deleteCompanyCoupon(int companyId, int couponId) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(int companyId, Category category) throws CouponSystemException;

    List<Coupon> getCompanyCoupons(int companyId, double maxPrice) throws CouponSystemException;

    Optional<Company> getCompanyDetails(int companyId);

    Optional<Coupon> getSingleCoupon(int couponId);

    Optional<Coupon> getSingleCompanyCoupon(int companyId, int couponId) throws CouponSystemException;
}
