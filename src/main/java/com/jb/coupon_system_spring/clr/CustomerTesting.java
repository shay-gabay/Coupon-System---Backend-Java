package com.jb.coupon_system_spring.clr;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.repos.CouponRepository;
import com.jb.coupon_system_spring.service.ClientService;
import com.jb.coupon_system_spring.service.CustomerService;
import com.jb.coupon_system_spring.utils.Art;
import com.jb.coupon_system_spring.utils.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@Component
@Order(4)
public class CustomerTesting implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CouponRepository couponRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Art.CUSTOMER_SERVICE_TEST);
        Test.printTitle(3, "Login");
        Test.test(3, "bad login - wrong email");
        try {
            ((ClientService) customerService).login("stam@gmail.com", "7281");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "bad login - wrong password");
        try {
            ((ClientService) customerService).login("david@gmail.com", "stam");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "bad login - wrong email & password");
        try {
            ((ClientService) customerService).login("stam@gmail.com", "stam");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "good login email & password");
        System.out.println(((ClientService) customerService).login("david@gmail.com", "7281"));

        Test.printTitle(3, "Customer coupon purchase");
        Test.test(3, "customer coupon's purchase - already purchase once ");
        Coupon couponToPurchse = customerService.getSingleCoupon(6, 6).orElseThrow(() -> new Exception("coupon not exist"));
        customerService.purchaseCoupon(1, couponToPurchse);
        try {
            customerService.purchaseCoupon(1, couponToPurchse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "customer coupon's purchase - amount zero");
        couponToPurchse = customerService.getSingleCoupon(1).orElseThrow(() -> new Exception("coupon not exist"));
        couponToPurchse.setAmount(0);
        couponRepository.saveAndFlush(couponToPurchse);//12
        try {
            customerService.purchaseCoupon(2, couponToPurchse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "customer coupon's purchase - date expired");
        couponToPurchse = customerService.getSingleCoupon(12).orElseThrow(() -> new Exception("coupon not exist"));
        couponToPurchse.setAmount(30);
        couponToPurchse.setEndDate(Date.valueOf(LocalDate.now()));
        couponRepository.saveAndFlush(couponToPurchse);//12
        try {
            couponToPurchse = customerService.getSingleCoupon(12).orElseThrow(() -> new Exception("coupon not exits"));
            customerService.purchaseCoupon(1, couponToPurchse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "customer coupon's purchase - success - ( amount minus 1 )");
        couponToPurchse = customerService.getSingleCoupon(4).orElseThrow(() -> new Exception("coupon not exist"));
        System.out.println("Before purchase - " + couponToPurchse);
        customerService.purchaseCoupon(1, couponToPurchse);
        System.out.println("After purchase - " + couponToPurchse);

        Test.printTitle(3, "Get customer coupons");
        Test.test(3, "get all customer coupons - not exist");
        ((ClientService) customerService).login("gil@gmail.com", "2096");
        try {
            customerService.getCustomerCoupons(3).stream().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "get all customer coupons - success");
        ((ClientService) customerService).login("david@gmail.com", "7281");
        try {
            customerService.getCustomerCoupons(1).stream().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Test.printTitle(3, "Get customer coupons by category");
        Test.test(3, "get all customer coupons - category - not exist");
        try {
            customerService.getCustomerCoupons(1, Category.HEALTH).stream().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "get all customer coupons - category - success");
        customerService.getCustomerCoupons(1, Category.SPORT).stream().forEach(System.out::println);

        Test.printTitle(3, "Get customer coupons by max price");
        Test.test(3, "get all customer coupons - max price - not exist");
        try {
            customerService.getCustomerCoupons(1, 10).stream().forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(3, "get all customer coupons - max price - success");
        customerService.getCustomerCoupons(1, 700).stream().forEach(System.out::println);

        Test.printTitle(3, "Customer details");
        Test.test(3, "get customer details");
        System.out.println(customerService.getCustomerDetails(1));

        Coupon coupon = new Coupon();
        coupon = customerService.getSingleCoupon(1).orElseThrow(() -> new Exception("Coupon not found"));
        coupon.setAmount(5000);
        couponRepository.save(coupon);

        Test.printTitle(3, "End");

    }

}
