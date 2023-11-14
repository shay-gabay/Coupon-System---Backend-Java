package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.exception.ErrMsg;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl extends ClientService implements CustomerService {

    @Override
    public boolean login(String email, String password) throws Exception {

        int customerId = getIdFromDB(email);
        if ((customerId == 0) && (!customerRepository.existsByPassword(password))) {
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_EMAIL_AND_PASSWORD);
        }
        if (customerId == 0) {
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_EMAIL);
        }
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new Exception("customer not exist"));
        if (!(customer.getPassword().equals(password))) {
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_PASSWORD);
        }
        // System.out.println("login success");
        return true;
    }

    @Override
    public int getIdFromDB(String email) {
        int id = 0;
        try {
            return id = customerRepository.getIdByEmail(email);
        } catch (Exception ignored) {
        }
        return id;
    }

    @Override
    public void purchaseCoupon(int customerId, Coupon coupon) throws CouponSystemException {
        if (couponRepository.existsByCouponIdAndCustomerId(coupon.getId(), customerId) == 1) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_COUPON_PURCHASE_INVALID);
        }
        if (coupon.getAmount() == 0) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_COUPON_PURCHASE_INVALID_AMOUNT_ZERO);
        }
        if (coupon.getEndDate().equals(Date.valueOf(LocalDate.now()))) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_COUPON_PURCHASE_INVALID_DATE_EXPIRED);
        }
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_NOT_EXIST));
        boolean b = true;
        try {
            customer.getCoupons().add(coupon);
            coupon.getCustomer().add(customer);
            customerRepository.saveAndFlush(customer);
            couponRepository.saveAndFlush(coupon);
        } catch (Exception e) {
            b = false;
            System.out.println(e.getMessage());
        }
        if (b) {
            coupon.setAmount(coupon.getAmount() - 1);
            couponRepository.saveAndFlush(coupon);
        }
    }

//    @Override
//    public List<Coupon> getAllCoupons() {
//        return couponRepository.findAll();
//    }

    @Override
    public Optional<Coupon> getSingleCoupon(int customerId, int couponId) throws CouponSystemException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        Coupon coupon = couponRepository.findByIdAndCustomer(couponId, customer).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_COUPONS_NOT_MATCH));
        return Optional.ofNullable(coupon);
    }

    @Override
    public Optional<Coupon> getSingleCoupon(int couponId) throws CouponSystemException {
        return couponRepository.findById(couponId);
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId) throws CouponSystemException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        List<Coupon> getCustomerCoupons = couponRepository.findByCustomer(customer);
        if (getCustomerCoupons.size() == 0) throw new CouponSystemException(ErrMsg.CUSTOMER_COUPONS_NOT_EXIST);
        return getCustomerCoupons;
    }

    @Override
    public List<Coupon> getCustomerCouponsNotPurchased(int customerId) throws CouponSystemException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        List<Coupon> getCustomerCoupons = couponRepository.findByCustomer(customer);
        if (getCustomerCoupons.size() == 0) throw new CouponSystemException(ErrMsg.CUSTOMER_COUPONS_NOT_EXIST);
        List<Coupon> allCoupons = couponRepository.findAll();
        List<Coupon> notPurchase = new ArrayList<>();
        for (Coupon coupon : allCoupons) {
            if (!getCustomerCoupons.contains(coupon)) {
                notPurchase.add(coupon);
            }
        }
        return notPurchase;
    }


    @Override
    public List<Coupon> getCustomerCoupons(int customerId, Category category) throws CouponSystemException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        List<Coupon> getCustomerCoupons = couponRepository.findByCustomerAndCategory(customer, category);
        if (getCustomerCoupons.size() == 0)
            throw new CouponSystemException(ErrMsg.CUSTOMER_COUPONS_BY_CATEGORY_NOT_EXIST);
        return getCustomerCoupons;
    }

    @Override
    public List<Coupon> getCustomerCoupons(int customerId, double maxPrice) throws CouponSystemException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        List<Coupon> getCustomerCoupons = couponRepository.findByCustomerAndPriceLessThan(customer, maxPrice + 1);
        if (getCustomerCoupons.size() == 0)
            throw new CouponSystemException(ErrMsg.CUSTOMER_COUPONS_BY_MAX_PRICE_NOT_EXIST);
        return getCustomerCoupons;
    }

    @Override
    public Customer getCustomerDetails(int customerId) throws CouponSystemException {
        return customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_NOT_EXIST));
    }
    public int CustomerCouponsCount(int customerId) {
        return couponRepository.customerCountCoupons(customerId);
    }
}
