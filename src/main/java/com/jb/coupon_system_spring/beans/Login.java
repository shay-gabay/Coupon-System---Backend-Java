package com.jb.coupon_system_spring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
@Data
@AllArgsConstructor
public class Login {
    private UUID token;
    private int id;
    private ClientType clientType;
    private String clientName;
}
