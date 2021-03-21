package com.couponet.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CouponType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int stock;
    private int maxPerUser; // Maximum number of coupon assigments by User;
}
