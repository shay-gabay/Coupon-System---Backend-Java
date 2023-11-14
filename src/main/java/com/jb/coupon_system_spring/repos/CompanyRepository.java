package com.jb.coupon_system_spring.repos;

import com.jb.coupon_system_spring.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    @Query(value = "SELECT id FROM `coupon_system_spring`.`companies` WHERE email=?", nativeQuery = true)
    int getIdByEmail(String email);

    @Query(value = "SELECT MAX(i) FROM `coupon_system_spring`.`companies`", nativeQuery = true)
    int maxIdOfCompanies();
}
