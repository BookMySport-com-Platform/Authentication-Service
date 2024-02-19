package com.bookmysport.authentication_service.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
// import org.springframework.format.annotation.NumberFormat;
import org.springframework.stereotype.Component;

@Entity
@Component
@Data
@Table(name = "user_details")
public class UserModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_id")
    private UUID id;

    @NotNull
    @Size(min = 3, max = 15)
    private String userName;

    @NotNull
    @Email(message = "Enter a valid email")
    private String email;

    private long phoneNumber;

    private boolean signInWithGoogle;

    private String password;

    public void setPassword(String password, boolean signInWithGoogle) {
        if (signInWithGoogle) {
            this.password = null;
        } else {
            this.password = password;
        }
        this.signInWithGoogle = signInWithGoogle;
    }

}
