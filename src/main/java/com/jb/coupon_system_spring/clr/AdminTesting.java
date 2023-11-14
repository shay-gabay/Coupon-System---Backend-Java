package com.jb.coupon_system_spring.clr;

import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.Customer;
import com.jb.coupon_system_spring.repos.CompanyRepository;
import com.jb.coupon_system_spring.repos.CouponRepository;
import com.jb.coupon_system_spring.repos.CustomerRepository;
import com.jb.coupon_system_spring.service.AdminService;
import com.jb.coupon_system_spring.service.ClientService;
import com.jb.coupon_system_spring.utils.Art;
import com.jb.coupon_system_spring.utils.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionException;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class AdminTesting implements CommandLineRunner {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
private CustomerRepository customerRepository;
    @Override
    public void run(String... args) throws Exception {
        System.out.println(Art.ADMIN_SERVICE_TEST);
        Test.printTitle(1, "Login");
        Test.test(1, "bad login - wrong email");
        try {
            ((ClientService) adminService).login("stam@gmail.com", "admin");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "bad login - wrong password");
        try {
            ((ClientService) adminService).login("admin@admin.com", "stam");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "bad login - wrong email & password");
        try {
            ((ClientService) adminService).login("stam@gmail.com", "stam");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "good login email & password");
        System.out.println(((ClientService) adminService).login("admin@admin.com", "admin"));

        Test.printTitle(1, "Add Company");
        Test.test(1, "add company - id already exist");
        Company companyToAdd = null;
        companyToAdd = adminService.getSingleCompany(1).orElseThrow(() -> new Exception("Company not exist !"));
        companyToAdd.setName("stam");
        companyToAdd.setEmail("stam@info.com");
        try {
            adminService.addCompany(companyToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "add company - name already exist");
        companyToAdd = adminService.getSingleCompany(1).orElseThrow(() -> new Exception("Company not exist !"));
        companyToAdd.setId(0);
        companyToAdd.setName("Coca Cola");
        companyToAdd.setEmail("stam@info.com");
        try {
            adminService.addCompany(companyToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "add company - email already exist");
        companyToAdd = adminService.getSingleCompany(1).orElseThrow(() -> new Exception("Company not exist !"));
        companyToAdd.setId(0);
        companyToAdd.setName("stam");
        try {
            adminService.addCompany(companyToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "add company - success");
        companyToAdd = Company.builder().id(11).name("new company").email("newCompany@info.com").password("8797").build();
        adminService.addCompany(companyToAdd);
        System.out.println(adminService.getSingleCompany(11));

        Test.printTitle(1, "Update Company");
        Test.test(1, "update company - cannot update id that not exist");
        Company companyToUpdate = null;
        companyToUpdate = this.adminService.getSingleCompany(1).orElseThrow(() -> new Exception("company not exist"));
        try {
            adminService.updateCompany(20, companyToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "update company - cannot update company's id");
        companyToUpdate = this.adminService.getSingleCompany(1).orElseThrow(() -> new Exception("company not exist"));
        companyToUpdate.setId(2);
        try {
            adminService.updateCompany(1, companyToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "update company - cannot update company's name");
        companyToUpdate = this.adminService.getSingleCompany(1).orElseThrow(() -> new Exception("company not exist"));
        companyToUpdate.setName("stam name");
        try {
            adminService.updateCompany(1, companyToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "update company - success");
        companyToUpdate = this.adminService.getSingleCompany(11).orElseThrow(() -> new Exception("company not exist"));
        companyToUpdate.setEmail("updated@info.com");
        companyToUpdate.setPassword("4321");
        adminService.updateCompany(11, companyToUpdate);
        System.out.println(adminService.getSingleCompany(11));

        Test.printTitle(1, "Delete Company");
        Test.test(1, "delete company invalid - company not exist");
        try {
            adminService.deleteCompany(13);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "delete company - success");
        System.out.println("The deleted company : " + adminService.getSingleCompany(11));
        adminService.deleteCompany(11);
        adminService.getAllCompanies().forEach(System.out::println);
        Test.printTitle(1, "Get All Companies");
        Test.test(1, "get all companies - success");
        adminService.getAllCompanies().forEach(System.out::println);
        Test.printTitle(1, "Get Single Company");
        Test.test(1, "get single company by id - company not exist");
        Company companyGetById = null;
        try {
            companyGetById = this.adminService.getSingleCompany(50).orElseThrow(() -> new Exception("Company not exist"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "get single company by id - success");
        companyGetById = this.adminService.getSingleCompany(1).orElseThrow(() -> new Exception("Company not exist"));
        System.out.println(companyGetById);

        Test.printTitle(1, "Add Customer");
        Test.test(1, "add a new customer - id already exist");
        Customer customerToAdd = null;
        customerToAdd = adminService.getSingleCustomer(1).orElseThrow(() -> new Exception("Customer not exist !"));
        customerToAdd.setFirstName("stam");
        customerToAdd.setLastName("stam");
        customerToAdd.setEmail("stam@info.com");
        try {
            adminService.addCustomer(customerToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "add a new customer - email already exist");
        customerToAdd = adminService.getSingleCustomer(1).orElseThrow(() -> new Exception("Customer not exist !"));
        customerToAdd.setId(0);
        customerToAdd.setFirstName("stam");
        customerToAdd.setLastName("stam");
        try {
            adminService.addCustomer(customerToAdd);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "add a new customer - success");
        customerToAdd = adminService.getSingleCustomer(1).orElseThrow(() -> new Exception("Customer not exist !"));
        customerToAdd.setId(0);
        customerToAdd.setFirstName("Menachem");
        customerToAdd.setLastName("Biton");
        customerToAdd.setEmail("menachem@gmail.com");
        adminService.addCustomer(customerToAdd);
        System.out.println(adminService.getSingleCustomer(11));

        Test.printTitle(1, "Update Customer");
        Test.test(1, "update customer - cannot update id that not exist");
        Customer customerToUpdate = null;
        try {
            adminService.updateCustomer(50, customerToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "update customer - cannot update customer's id");
        customerToUpdate = adminService.getSingleCustomer(1).orElseThrow(() -> new Exception("Customer not exist !"));
        customerToUpdate.setId(2);
        try {
            adminService.updateCustomer(1, customerToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "update customer - email already exist");
        customerToUpdate = adminService.getSingleCustomer(1).orElseThrow(() -> new Exception("Customer not exist !"));
        customerToUpdate.setFirstName("Moshe");
        customerToUpdate.setLastName("Ben Attar");
        customerToUpdate.setEmail("moshe@gmail.com");
        try {
            adminService.updateCustomer(1, customerToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "update customer - success");
        customerToUpdate = adminService.getSingleCustomer(11).orElseThrow(() -> new Exception("Customer not exist !"));
        customerToUpdate.setFirstName("Ofir");
        customerToUpdate.setLastName("Navon");
        customerToUpdate.setEmail("ofir@gmail.com");
        adminService.updateCustomer(11, customerToUpdate);
        System.out.println(adminService.getSingleCustomer(11));

        Test.printTitle(1, "Delete Customer");
        Test.test(1, "delete customer - customer id not exist");
        try {
            adminService.deleteCustomer(50);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "delete customer - success");
        System.out.println("Customer to delete : \n" + adminService.getSingleCustomer(11) + "\n");
        adminService.deleteCustomer(11);
        System.out.println("The remaining customers :");
        adminService.getAllCustomers().forEach(System.out::println);

        Test.printTitle(1, "Get All Customers");
        Test.test(1, "get all customers");
        adminService.getAllCustomers().forEach(System.out::println);

        Test.printTitle(1, "Get Single Customer");
        Test.test(1, "get single customer by id - customer not exist");
        Customer customerGetById = null;
        try {
            customerGetById = adminService.getSingleCustomer(50).orElseThrow(() -> new Exception("Customer not exist"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Test.test(1, "get a single customer by id - success");
        customerGetById = adminService.getSingleCustomer(2).orElseThrow(() -> new Exception("Customer not exist"));
        System.out.println(customerGetById);

        Test.printTitle(1, "End");

    }

}