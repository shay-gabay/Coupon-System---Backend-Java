package com.jb.coupon_system_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CouponSystemSpringApplication {

	public static void main(String[] args) {
		System.out.println("START");
		SpringApplication.run(CouponSystemSpringApplication.class, args);
		System.out.println("END");
	}
}
