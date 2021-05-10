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
    private int stock;
    private int maxPerUser;
    private LocalDateTime registrationTime = LocalDateTime.now();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User ownerUser;

    public Coupon(float price, int stock, int maxPerUser, User ownerUser) {
        this.price = price;
        this.stock = stock;
        this.maxPerUser = maxPerUser;
        this.ownerUser = ownerUser;
    }
}