package com.jb.coupon_system_spring.advic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrDetails {

    private final String title = "COUPON SYSTEM ERROR";
    private String description;
}
