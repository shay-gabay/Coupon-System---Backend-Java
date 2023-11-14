package com.jb.coupon_system_spring.jobs;


import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.repos.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
//@Order(5)
//@Component
public class DailyRemovalExpired {
    @Autowired
    private CouponRepository couponRepository;
    private final int dayTime = 1000*60*60*24;

    //@Scheduled(fixedRate = dayTime)
    public void Removal() {
        List<Coupon> coupons = new ArrayList<>();
        coupons = couponRepository.expiredCoupons();
        if (coupons != null) {
            for (Coupon coupon : coupons) {
                System.out.println("************ Expired Coupon Delete - "+coupon);
                this.couponRepository.deleteCouponVsCustomer(coupon.getId());
                this.couponRepository.delete(coupon);
            }
        }
    }
}
