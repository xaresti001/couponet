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

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Coupon coupon;

    private LocalDateTime registrationDateTime = LocalDateTime.now();

    public Assigment(User user, Coupon coupon) {
        this.user = user;
        this.coupon = coupon;
    }
}