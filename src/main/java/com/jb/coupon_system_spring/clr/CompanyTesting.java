package com.jb.coupon_system_spring.clr;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.repos.CompanyRepository;
import com.jb.coupon_system_spring.repos.CouponRepository;
import com.jb.coupon_system_spring.service.ClientService;
import com.jb.coupon_system_spring.service.CompanyService;
import com.jb.coupon_system_spring.utils.Art;
import com.jb.coupon_system_spring.utils.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@Order(3)
public class CompanyTesting implements CommandLineRunner {
    @Autowired
    CompanyService companyService;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(Art.COMPANY_SERVICE_TEST);
        Test.printTitle(2, "Login");
        Test.test(2, "bad login - wrong email");
        try {
            ((ClientService) companyService).login("stam@gmail.com", "8218");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "bad login - wrong password");
        try {
            ((ClientService) companyService).login("bug@info.com", "1111");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "bad login - wrong email & password");
        try {
            ((ClientService) companyService).login("stam@info.com", "stam");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "good login email & password");
        System.out.println(((ClientService) companyService).login("bug@info.com", "8218"));

        Test.printTitle(2, "Add Coupon");
        Test.test(2, "add coupon - title already exist");
        Coupon couponToAdd = Coupon.builder()
                .company(companyRepository.findById(4).orElseThrow(()->new Exception("company not exist")))
                .category(Category.SPORT)
                .title("Dreams comes true...")
                .description("buy sports shooz 1+1")
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now().plusDays(5)))
                .amount(20)
                .price(100)
                .image("image")
                .build();
        try {
            companyService.addCoupon(couponToAdd.getCompany().getId(), couponToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "add coupon - title already exist in other company");
        couponToAdd.setTitle("Enjoy yourself ...");
        companyService.addCoupon(couponToAdd.getCompany().getId(),couponToAdd);
        System.out.println("The added coupon : " + couponToAdd);
        System.out.println("The same title coupon : " + companyService.getSingleCoupon(3));
        System.out.println("Add successfuly : " + couponRepository.findById(11));
        Test.test(2, "add coupon - success");
        couponToAdd.setTitle("You must run to us...");
        couponToAdd.setId(0);
        companyService.addCoupon(couponToAdd.getCompany().getId(),couponToAdd);
        System.out.println(couponRepository.findById(12));

        Test.printTitle(2, "Update company's coupons");
        Test.test(2, "update coupon's id - invalid");
        Coupon couponToUpdat = companyService.getSingleCoupon(5).orElseThrow(() -> new Exception("coupon not found"));
        couponToUpdat.setId(12);
        try {
            //couponToUpdat.getCompany().getId()
            companyService.updateCoupon(5, couponToUpdat);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "update company's id in coupon - invalid");
        couponToUpdat.setId(5);
        Company company = companyRepository.findById(1).orElseThrow(()->new Exception("company not exist"));
        company.setId(77);
        couponToUpdat.setCompany(company);
        try {
            //couponToUpdat.getCompany().getId()
            companyService.updateCoupon(5, couponToUpdat);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "update coupon - success");
        ((ClientService) companyService).login("alufhasport@info.com", "7117");
        couponToUpdat = companyService.getSingleCoupon(12).orElseThrow(() -> new Exception("company not exist"));
        System.out.println("Before update - " + couponToUpdat);
        couponToUpdat.setTitle("Updated coupon");
        couponToUpdat.setDescription("Updated coupon");
        couponToUpdat.setId(12);
        couponRepository.saveAndFlush(couponToUpdat);
        System.out.println("After update - " + companyService.getSingleCoupon(12));

        Test.printTitle(2, "Get company's coupons");
        Test.test(2, "get all login company's coupons ");
        int companyId = couponToUpdat.getCompany().getId();
        companyService.getCompanyCoupons(companyId).forEach(System.out::println);
        Test.printTitle(2, "Company coupon's by category");
        Test.test(2, "get all company's coupons in one category - not exist");
        try {
            companyService.getCompanyCoupons(companyId,Category.FOOD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "get all company's coupons in one category - success");
        try {
            companyService.getCompanyCoupons(companyId,Category.SPORT).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Test.printTitle(2, "Company coupon's by max price");
        Test.test(2, "get all company's coupons to max price - not exist");
        try {
            companyService.getCompanyCoupons(companyId,90).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(2, "get all company's coupons to max price - success");
        try {
            companyService.getCompanyCoupons(companyId,650).forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Test.printTitle(2, "Company details");
        Test.test(2, "get company details");
        System.out.println(companyService.getCompanyDetails(companyId));

        Test.printTitle(2, "End");
    }


}

