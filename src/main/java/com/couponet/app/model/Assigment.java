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
public class Assigment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int clientId;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Coupon coupon;

    private LocalDateTime registrationDateTime = LocalDateTime.now();

    public Assigment(int clientId, Coupon coupon) {
        this.clientId = clientId;
        this.coupon = coupon;
    }
}