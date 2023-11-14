package com.jb.coupon_system_spring.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.List;
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Entity
@Table(name = "companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private String image;
    @OneToMany(mappedBy = "company")//,cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @ToString.Exclude
    @JsonIgnore
    @Singular
    private List<Coupon> coupons = new ArrayList<>();
}
