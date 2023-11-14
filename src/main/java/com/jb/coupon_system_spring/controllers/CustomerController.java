package com.jb.coupon_system_spring.controllers;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.exception.ErrMsg;
import com.jb.coupon_system_spring.repos.CouponRepository;
import com.jb.coupon_system_spring.security.TokenService;
import com.jb.coupon_system_spring.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
@RequestMapping("api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    TokenService tokenService;

    @PostMapping("{customerId}/coupons/{couponId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void purchaseCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int customerId, @PathVariable int couponId) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.CUSTOMER);
        Coupon coupon = customerService.getSingleCoupon(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COUPON_NOT_EXIST));
        customerService.purchaseCoupon(customerId, coupon);
    }

    @GetMapping("/{customerId}")
    public Customer getCustomerDetails(@RequestHeader("Authorization") UUID token, @PathVariable int customerId) throws CouponSystemException {
        return customerService.getCustomerDetails(customerId);
    }

    @GetMapping("{customerId}/coupons")
    public List<Coupon> getCustomerCoupons(@PathVariable int customerId) throws CouponSystemException {
        return customerService.getCustomerCoupons(customerId);
    }

    @GetMapping("{customerId}/coupons/{couponId}")
    public Optional<Coupon> getSingleCoupon(@RequestHeader("Authorization") UUID token, @PathVariable int customerId, @PathVariable int couponId) throws CouponSystemException {
        return customerService.getSingleCoupon(customerId, couponId);
    }

    @GetMapping("{customerId}/coupons/category")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token, @PathVariable int customerId, @RequestParam Category category) throws CouponSystemException {
        return customerService.getCustomerCoupons(customerId, category);
    }

    @GetMapping("{customerId}/coupons/maxPrice")
    public List<Coupon> getCustomerCoupons(@RequestHeader("Authorization") UUID token, @PathVariable int customerId, @RequestParam double maxPrice) throws CouponSystemException {
        return customerService.getCustomerCoupons(customerId, maxPrice);
    }

    @GetMapping("/coupons")
    public List<Coupon> getAllCoupons(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.ADMINISTRATOR);
        List<Coupon> coupons = couponRepository.findAll();
        if (coupons.isEmpty()) {
            throw new CouponSystemException(ErrMsg.SYSTEM_COUPONS_NOT_EXIST);
        }
        return coupons;
    }

    @GetMapping("{customerId}/customerCouponsNotPurchased")
    public List<Coupon> getCustomerCouponsNotPurchased(@RequestHeader("Authorization") UUID token, @PathVariable int customerId) throws CouponSystemException {
        tokenService.isAllowed(token, ClientType.CUSTOMER);
        return customerService.getCustomerCouponsNotPurchased(customerId);
    }
}
