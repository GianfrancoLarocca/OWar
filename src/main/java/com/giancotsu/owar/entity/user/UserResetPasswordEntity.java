package com.giancotsu.owar.entity.user;

import com.giancotsu.owar.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Entity
@Table(name = "user_reset_password")
@Data
public class UserResetPasswordEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secureCode;
    private Date expirationDate;

    public UserResetPasswordEntity() {
    }

    public UserResetPasswordEntity(String secureCode) {
        this.secureCode = secureCode;
        this.expirationDate = new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24);
    }

    @OneToOne(mappedBy = "resetPasswordEntity")
    private UserEntity user;
}
