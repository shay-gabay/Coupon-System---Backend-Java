package com.jb.coupon_system_spring.security;

import com.jb.coupon_system_spring.beans.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Information {
    private int id;
    private String email;
    private LocalDateTime time;
    private ClientType clientType;
}
