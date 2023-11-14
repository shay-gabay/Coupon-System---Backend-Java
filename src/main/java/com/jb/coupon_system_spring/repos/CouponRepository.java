package com.jb.coupon_system_spring.repos;

import com.jb.coupon_system_spring.beans.Category;
import com.jb.coupon_system_spring.beans.Company;
import com.jb.coupon_system_spring.beans.Coupon;
import com.jb.coupon_system_spring.beans.Customer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CouponRepository extends JpaRepository<Coupon, Integer> {
    boolean existsByTitleAndCompanyId(String title, int companyId);

    @Override
    List<Coupon> findAll();

    List<Coupon> findByCompany(Company company);

    @Query(value = "SELECT * FROM coupon_system_spring.coupons where company_id=? And id=?;", nativeQuery = true)
    Optional<Coupon> findByCompanyIdAndCouponId(int companyId, int couponId);

    List<Coupon> findByCompanyAndCategory(Company company, Category category);

    List<Coupon> findByCompanyAndPriceLessThan(Company company, double maxPrice);

    List<Coupon> findByCustomer(Customer customer);

    Optional<Coupon> findByIdAndCustomer(int couponId, Customer customer);

    List<Coupon> findByCustomerAndCategory(Customer customer, Category category);

    List<Coupon> findByCustomerAndPriceLessThan(Customer customer, double maxPrice);

    @Query(value = "SELECT EXISTS (SELECT * FROM coupon_system_spring.coupons_customer WHERE `coupons_id`=? AND `customer_id`=? ) AS res", nativeQuery = true)
    int existsByCouponIdAndCustomerId(int couponId, int customerId);

    @Query(value = "SELECT EXISTS (SELECT * FROM coupon_system_spring.coupons WHERE `company_id`=? AND `id`=?) AS res", nativeQuery = true)
    int existsByCompanyIdAndCouponId(int companyId, int couponId);

    @Modifying
    @Query(value = "DELETE FROM coupon_system_spring.coupons_customer WHERE coupons_id=?", nativeQuery = true)
    void deleteCouponVsCustomer(int couponId);

    @Modifying
    @Query(value = "DELETE FROM coupon_system_spring.coupons_customer WHERE customer_id=?", nativeQuery = true)
    void deleteCustomerCoupons(int customerId);

    @Modifying
    @Query(value = "DELETE FROM coupon_system_spring.coupons WHERE company_id=?", nativeQuery = true)
    void deleteCompanyCoupons(int companyId);

    @Query(value = "SELECT * FROM coupon_system_spring.coupons WHERE amount=0;", nativeQuery = true)
    List<Coupon> expiredCoupons();

    @Query(value = "SELECT COUNT(*) FROM coupon_system_spring.coupons_customer WHERE customer_id = ?;", nativeQuery = true)
    int customerCountCoupons(int customerId);

    @Query(value = "SELECT COUNT(*) FROM coupon_system_spring.coupons WHERE company_id=?;", nativeQuery = true)
    int companyCountCoupons(int companyId);

}
