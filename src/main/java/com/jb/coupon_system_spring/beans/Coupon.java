package com.jb.coupon_system_spring.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "coupons")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne  //(cascade = {CascadeType.REMOVE})//,CascadeType.PERSIST})
//    @JsonIgnore
    @ToString.Exclude
    private Company company;
    @ManyToMany  //(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonIgnore
    @ToString.Exclude
    private List<Customer> customer;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;

}
