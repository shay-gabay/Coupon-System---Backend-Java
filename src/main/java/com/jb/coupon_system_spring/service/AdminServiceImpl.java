package com.jb.coupon_system_spring.service;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.exception.ErrMsg;
import com.jb.coupon_system_spring.repos.CustomerRepository;
import jdk.jshell.spi.ExecutionControlProvider;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl extends ClientService implements AdminService {

    @Override
    public boolean login(String email, String password) throws CouponSystemException {
        if ((!email.equals("admin@admin.com")) && (!password.equals("admin")))
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_EMAIL_AND_PASSWORD);
        if (!email.equals("admin@admin.com"))
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_EMAIL);
        if (!password.equals("admin"))
            throw new CouponSystemException(ErrMsg.LOGIN_MANAGER_INVALID_PASSWORD);
        // System.out.println("login success");
        return true;
    }

    @Override
    public void addCompany(Company company) throws CouponSystemException {
        int id = company.getId();
        if (this.companyRepository.existsById(id)) {
            throw new CouponSystemException(ErrMsg.ADD_COMPANY_ID_EXIST);
        }
        String name = company.getName();
        if (this.companyRepository.existsByName(name)) {
            throw new CouponSystemException(ErrMsg.ADD_COMPANY_NAME_EXIST);
        }
        String email = company.getEmail();
        if (this.companyRepository.existsByEmail(email)) {
            throw new CouponSystemException(ErrMsg.ADD_COMPANY_EMAIL_EXIST);
        }
        authService.addUser(company.getEmail(),company.getPassword(), ClientType.COMPANY);
        this.companyRepository.save(company);
    }

    @Override // not necessary
    public int getIdFromDB(String email) {
        return 0;
    }

    @Override
    public void updateCompany(int companyId, Company company) throws CouponSystemException {
        if (!this.companyRepository.existsById(companyId)) {
            throw new CouponSystemException(ErrMsg.UPDATE_COMPANY_ID_NOT_EXIST);
        }
        if (companyId != company.getId()) {
            throw new CouponSystemException(ErrMsg.UPDATE_COMPANY_CANNOT_UPDATE_ID);
        }
        Company fromDb = this.companyRepository.findById(companyId).orElseThrow(()-> new CouponSystemException(ErrMsg.COMPANY_NOT_EXIST));
        if (!fromDb.getName().equals(company.getName())) {
            throw new CouponSystemException(ErrMsg.UPDATE_COMPANY_CANNOT_UPDATE_NAME);
        }

        int id = userRepository.getIdByEmail(companyRepository.findById(companyId).get().getEmail());
        authService.updateUser(id,company.getEmail(),company.getPassword(),ClientType.COMPANY);
        this.companyRepository.saveAndFlush(company);
    }

    @Override
    public Optional<Company> getSingleCompany(int companyId) throws CouponSystemException {
        if (!companyRepository.existsById(companyId)) {
            throw new CouponSystemException(ErrMsg.COMPANY_NOT_EXIST);
        }
        return this.companyRepository.findById(companyId);
    }

    @Override
    public void deleteCompany(int companyId) throws CouponSystemException {
        if (!this.companyRepository.existsById(companyId)) {
            throw new CouponSystemException(ErrMsg.DELETE_COMPANY_ID_NOT_EXIST);
        }
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_NOT_EXIST));
        List<Coupon> coupons = couponRepository.findByCompany(company);
        if (coupons != null) {
            coupons.stream().forEach(coupon -> couponRepository.deleteCouponVsCustomer(coupon.getId()));
        }
        int id = userRepository.getIdByEmail(companyRepository.findById(companyId).get().getEmail());
        authService.deleteUser(id);
        this.couponRepository.deleteCompanyCoupons(companyId);
        this.companyRepository.deleteById(companyId);
    }

    @Override
    public List<Company> getAllCompanies() {
        return this.companyRepository.findAll();
    }

    @Override
    public void addCustomer(Customer customer) throws CouponSystemException {
        int id = customer.getId();
        if (this.companyRepository.existsById(id)) {
            throw new CouponSystemException(ErrMsg.ADD_CUSTOMER_ID_EXIST);
        }
        String email = customer.getEmail();
        if (this.customerRepository.existsByEmail(email)) {
            throw new CouponSystemException(ErrMsg.ADD_CUSTOMER_EMAIL_EXIST);
        }
        authService.addUser(customer.getEmail(),customer.getPassword(), ClientType.CUSTOMER);
        this.customerRepository.save(customer);
    }

    @Override
    public void updateCustomer(int customerId, Customer customer) throws CouponSystemException {
        if (!this.customerRepository.existsById(customerId)) {
            throw new CouponSystemException(ErrMsg.UPDATE_CUSTOMER_ID_NOT_EXIST);
        }
        if (customerId != customer.getId()) {
            throw new CouponSystemException(ErrMsg.UPDATE_CUSTOMER_CANNOT_UPDATE_ID);
        }
        String oldEmail = customerRepository.findById(customerId).get().getEmail();
        String newEmail = customer.getEmail();
        if (!oldEmail.equals(newEmail)) {
            if (this.customerRepository.existsByEmail(newEmail)) {
                throw new CouponSystemException(ErrMsg.UPDATE_CUSTOMER_EMAIL_EXIST);
            }
        }
        int id = userRepository.getIdByEmail(customerRepository.findById(customerId).get().getEmail());
        authService.updateUser(id,customer.getEmail(),customer.getPassword(),ClientType.CUSTOMER);
        this.customerRepository.saveAndFlush(customer);
    }

    @Override
    public void deleteCustomer(int customerId) throws CouponSystemException {
        if (!this.customerRepository.existsById(customerId)) {
            throw new CouponSystemException(ErrMsg.DELETE_CUSTOMER_ID_NOT_EXIST);
        }
        int id = userRepository.getIdByEmail(customerRepository.findById(customerId).get().getEmail());
        authService.deleteUser(id);
        this.couponRepository.deleteCustomerCoupons(customerId);
        this.customerRepository.deleteById(customerId);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    @Override
    public Optional<Customer> getSingleCustomer(int customerId) throws CouponSystemException {
        if (!customerRepository.existsById(customerId)) {
            throw new CouponSystemException(ErrMsg.CUSTOMER_NOT_EXIST);
        }
        return this.customerRepository.findById(customerId);
    }
}