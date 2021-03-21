package com.couponet.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private float price;
    private LocalDateTime registrationTime = LocalDateTime.now();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CouponType couponType;
}
