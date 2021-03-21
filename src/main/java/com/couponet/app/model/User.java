package com.couponet.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private int id;
    private String name;
    private String lastName;
    private int telephoneNumber;
    private LocalDateTime registrationDateTime = LocalDateTime.now();

    private enum Role{
        CLIENT,
        ATTENDANT,
        MANAGER,
        ADMIN
    }
    private Role role;
}
