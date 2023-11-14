package com.jb.coupon_system_spring.repos;

import com.jb.coupon_system_spring.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsByEmail(String email);
    boolean existsByPassword(String password);
    @Query(value = "SELECT * FROM coupon_system_spring.customers WHERE email = (SELECT email FROM coupon_system_spring.customers WHERE id = ?) AND id != ?;",nativeQuery = true)
    int existEmailAnotherCustomer(int customerId);
    @Modifying
    @Query(value = "DELETE FROM coupon_system_spring.customers WHERE id=?;", nativeQuery = true)
    void deleteCustomer(int id);

    //  PasswordAuthentication getSingle(int customerId);
    @Query(value = "SELECT id FROM `coupon_system_spring`.`customers` WHERE email=?", nativeQuery = true)
    int getIdByEmail(String email);
}
