package com.jb.coupon_system_spring.controllers;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.exception.ErrMsg;
import com.jb.coupon_system_spring.security.TokenService;
import com.jb.coupon_system_spring.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
@RequestMapping("api/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @Autowired
    TokenService tokenService;

    @PostMapping("{companyId}/coupons")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int companyId, @RequestBody Coupon coupon) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        companyService.addCoupon(companyId,coupon);
    }

    @PutMapping("{companyId}/coupon/{couponId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int companyId,@PathVariable int couponId, @RequestBody Coupon coupon) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        companyService.updateCoupon(companyId,couponId, coupon);

    }

    @DeleteMapping("{companyId}/coupons/{couponId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int companyId,@PathVariable int couponId) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        companyService.deleteCompanyCoupon(companyId,couponId);
    }

    @GetMapping("{companyId}/coupons")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        return companyService.getCompanyCoupons(companyId);
    }

    @GetMapping("{companyId}/coupons/category")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @PathVariable int companyId, @RequestParam Category category) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        return companyService.getCompanyCoupons(companyId, category);
    }

    @GetMapping("{companyId}/coupons/maxPrice")
    public List<Coupon> getCompanyCoupons(@RequestHeader("Authorization") UUID token, @PathVariable int companyId, @RequestParam double maxPrice) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        return companyService.getCompanyCoupons(companyId, maxPrice);
    }

    @GetMapping("/{companyId}")
    public Company getCompanyDetails(@RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        return companyService.getCompanyDetails(companyId).orElseThrow(()->new CouponSystemException(ErrMsg.COMPANY_NOT_EXIST));
    }

    @GetMapping("{companyId}/coupons/{couponId}")
    public Coupon getSingleCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int companyId,@PathVariable int couponId) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.COMPANY);
        return companyService.getSingleCompanyCoupon(companyId,couponId).orElseThrow(()->new CouponSystemException(ErrMsg.COUPON_NOT_EXIST));
    }

}
