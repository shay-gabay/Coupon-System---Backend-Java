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
import java.util.List;
import java.util.Optional;


@Service
public class CompanyServiceImpl extends ClientService implements CompanyService {

    //  private int companyId;

    @Override
    public boolean login(String email, String password) throws Exception {
        int companyId = getIdFromDB(email);
        if ((companyId == 0) && (!companyRepository.existsByPassword(password))) {
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_EMAIL_AND_PASSWORD);
        }
        if (companyId == 0) {
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_EMAIL);
        }
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new Exception("company not exist"));
        if (!(company.getPassword().equals(password))) {
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_PASSWORD);
        }
        // System.out.println("login success");
        return true;
    }

    @Override
    public int getIdFromDB(String email) {
        int id = 0;
        try {
            return id = companyRepository.getIdByEmail(email);
            //return id = companyRepository.findCompanyIdByEmail(email);
        } catch (Exception ignored) {
        }
        return id;
    }

    @Override
    public void addCoupon(int companyId, Coupon coupon) throws CouponSystemException {
        int couponCompanyId = companyId;
        if (coupon.getCompany() != null) {
            couponCompanyId = coupon.getCompany().getId();
        }
        if (couponRepository.existsByTitleAndCompanyId(coupon.getTitle(), couponCompanyId)) {
            throw new CouponSystemException(ErrMsg.ADD_COUPON_TITLE_EXIST);
        }
        Date date = Date.valueOf(LocalDate.now());
        if (coupon.getStartDate().before(date) || coupon.getEndDate().before(date)) {
            throw new CouponSystemException(ErrMsg.COUPON_DATE_INVALID);
        }
        coupon.setCompany(companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_NOT_EXIST)));
        couponRepository.save(coupon);
    }

    @Override
    public void updateCoupon(int companyId, int couponId, Coupon coupon) throws CouponSystemException {
        if (couponRepository.findById(couponId).get().getCompany().getId() != companyId) {
            throw new CouponSystemException(ErrMsg.COMPANY_COUPONS_NOT_MATCH);
        }
        Coupon coupon1 = couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COUPON_NOT_EXIST));
        int couponId1 = coupon1.getId();
        if (couponId1 == 0) {
            throw new CouponSystemException(ErrMsg.INVALID_COUPON_ID);
        }
        if ((coupon1.getId()) != (coupon.getId())) {
            throw new CouponSystemException(ErrMsg.UPDATE_COUPONS_ID_INVALID);
        }
        if (coupon.getEndDate().before(coupon.getStartDate())) {
            throw new CouponSystemException(ErrMsg.COUPON_DATE_INVALID);
        }
        Company company = coupon1.getCompany();
        Company company1 = coupon.getCompany();
        if (company != null && company1 != null) {
            if ((company.getId()) != (company1.getId())) {
                throw new CouponSystemException(ErrMsg.UPDATE_COUPONS_COMPANY_ID_INVALID);
            }
        }
        coupon.setId(couponId);
        coupon.setCompany(company);
        coupon.setCustomer(coupon1.getCustomer());
        couponRepository.saveAndFlush(coupon);
    }

    @Override
    public void updateCoupon(int couponId, Coupon coupon) throws CouponSystemException {
        Coupon coupon1 = couponRepository.findById(couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COUPON_NOT_EXIST));
        int couponId1 = coupon1.getId();
        if (couponId1 == 0) {
            throw new CouponSystemException(ErrMsg.INVALID_COUPON_ID);
        }
        if ((coupon1.getId()) != (coupon.getId())) {
            throw new CouponSystemException(ErrMsg.UPDATE_COUPONS_ID_INVALID);
        }
        Company company = coupon1.getCompany();
        Company company1 = coupon.getCompany();
        if (company != null && company1 != null) {
            if ((company.getId()) != (company1.getId())) {
                throw new CouponSystemException(ErrMsg.UPDATE_COUPONS_COMPANY_ID_INVALID);
            }
        }
        coupon.setId(couponId);
        coupon.setCompany(company);
        coupon.setCustomer(coupon1.getCustomer());
        couponRepository.saveAndFlush(coupon);
    }

    @Override
    public void deleteCoupon(int couponId) throws CouponSystemException {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponSystemException(ErrMsg.DELETE_COUPONS_NOT_EXIST);
        }
        couponRepository.deleteCouponVsCustomer(couponId);
        couponRepository.deleteById(couponId);
    }

    @Override
    public void deleteCompanyCoupon(int companyId, int couponId) throws CouponSystemException {
        if (!couponRepository.existsById(couponId)) {
            throw new CouponSystemException(ErrMsg.DELETE_COUPONS_NOT_EXIST);
        }
        if (couponRepository.existsByCompanyIdAndCouponId(companyId, couponId) == 0) {
            throw new CouponSystemException(ErrMsg.COMPANY_COUPONS_NOT_MATCH);
        }
        couponRepository.deleteCouponVsCustomer(couponId);
        couponRepository.deleteById(couponId);
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId) throws CouponSystemException {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        List<Coupon> companyCoupons = couponRepository.findByCompany(company);
        if (companyCoupons == null) {
            throw new CouponSystemException(ErrMsg.COMPANY_COUPONS_NOT_EXIST);
        }
        return companyCoupons;
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId, Category category) throws CouponSystemException {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        List<Coupon> coupons = couponRepository.findByCompanyAndCategory(company, category);
        if (coupons.size() == 0) {
            throw new CouponSystemException(ErrMsg.COMPANY_CATEGORY_COUPONS_NOT_EXIST);
        }
        return coupons;
    }

    @Override
    public List<Coupon> getCompanyCoupons(int companyId, double maxPrice) throws CouponSystemException {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        List<Coupon> coupons = couponRepository.findByCompanyAndPriceLessThan(company, maxPrice + 1);
        if (coupons.size() == 0) {
            throw new CouponSystemException(ErrMsg.COMPANY_MAX_PRICE_COUPONS_NOT_EXIST);
        }
        return coupons;
    }

    @Override
    public Optional<Company> getCompanyDetails(int companyId) {
        return companyRepository.findById(companyId);
    }

    @Override
    public Optional<Coupon> getSingleCoupon(int couponId) {
        return couponRepository.findById(couponId);
    }

    @Override
    public Optional<Coupon> getSingleCompanyCoupon(int companyId, int couponId) throws CouponSystemException {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_COMPANY_ID_NOT_EXIST));
        Coupon coupon = couponRepository.findByCompanyIdAndCouponId(companyId, couponId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_COUPONS_NOT_MATCH));
        return Optional.ofNullable(coupon);
    }

    public Optional<Coupon> getSingleCoupon(int customerId, int couponId) throws CouponSystemException {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.GET_CUSTOMER_ID_NOT_EXIST));
        Coupon coupon = couponRepository.findByIdAndCustomer(couponId, customer).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_COUPONS_NOT_MATCH));
        return Optional.ofNullable(coupon);
    }

    public int CompanyCouponsCount(int companyId) {
        return couponRepository.companyCountCoupons(companyId);
    }

}