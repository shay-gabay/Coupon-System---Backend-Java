package com.jb.coupon_system_spring.controllers;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.exception.CouponSystemException;
import com.jb.coupon_system_spring.exception.ErrMsg;
import com.jb.coupon_system_spring.repos.CustomerRepository;
import com.jb.coupon_system_spring.security.TokenService;
import com.jb.coupon_system_spring.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "Authorization"}, allowCredentials = "true")
@RestController
@RequestMapping("api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private TokenService tokenService;
    @PostMapping("/company")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCompany(@RequestHeader("Authorization") UUID token, @RequestBody Company company) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        adminService.addCompany(company);
    }

    @PutMapping("/company/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCompany(@RequestHeader("Authorization") UUID token, @PathVariable int companyId, @RequestBody Company company) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        adminService.updateCompany(companyId, company);
    }

    @DeleteMapping("/company/{companyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        adminService.deleteCompany(companyId);
    }

    @GetMapping("/company")
    public List<Company> getAllCompanies(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        return adminService.getAllCompanies();
    }

    @GetMapping("/company/{companyId}")
    public Company getSingleCompany(@RequestHeader("Authorization") UUID token, @PathVariable int companyId) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        return adminService.getSingleCompany(companyId).orElseThrow(() -> new CouponSystemException(ErrMsg.COMPANY_NOT_EXIST));
    }

    @PostMapping("/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public void addCustomer(@RequestHeader("Authorization") UUID token, @RequestBody Customer customer) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        adminService.addCustomer(customer);
    }

    @PutMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int customerId, @RequestBody Customer customer) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        adminService.updateCustomer(customerId, customer);
    }

    @DeleteMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int customerId) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        adminService.deleteCustomer(customerId);
    }

    @GetMapping("/customer")
    public List<Customer> getAllCustomers(@RequestHeader("Authorization") UUID token) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        return adminService.getAllCustomers();
    }

    @GetMapping("/customer/{customerId}")
    public Customer getSingleCustomer(@RequestHeader("Authorization") UUID token, @PathVariable int customerId) throws CouponSystemException {
        tokenService.isAllowed(token,ClientType.ADMINISTRATOR);
        return adminService.getSingleCustomer(customerId).orElseThrow(() -> new CouponSystemException(ErrMsg.CUSTOMER_NOT_EXIST));
    }

}