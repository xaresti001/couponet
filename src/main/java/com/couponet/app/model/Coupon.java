package com.couponet.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    private int id;
    private float price;
    private LocalDateTime registrationTime = LocalDateTime.now();

    @ManyToOne
    private CouponType couponType;
}
