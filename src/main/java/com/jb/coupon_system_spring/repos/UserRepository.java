package com.jb.coupon_system_spring.repos;

import com.jb.coupon_system_spring.beans.ClientType;
import com.jb.coupon_system_spring.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    boolean existsByEmail(String email);

    boolean existsByPassword(String password);

    boolean existsByEmailAndPasswordAndClientType(String email, String password, ClientType ClientType);

    @Query(value = "SELECT id FROM `coupon_system_spring`.`user` WHERE email=?", nativeQuery = true)
    int getIdByEmail(String email);
}
