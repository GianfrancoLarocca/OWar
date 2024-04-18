package com.giancotsu.owar.entity.user;

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

    @OneToOne(mappedBy = "resetPasswordEntity")
    private UserEntity user;

    public UserResetPasswordEntity() {
    }

    public UserResetPasswordEntity(String secureCode) {
        this.secureCode = secureCode;
        this.expirationDate = getExpirationValue();
    }

    public void setSecureCode(String secureCode) {
        this.secureCode = secureCode;
        this.expirationDate = getExpirationValue();
    }

    private Date getExpirationValue() {
        long tempo = 1000 * 60 * 60 * 24L;
        return new Date(System.currentTimeMillis() + tempo);
    }
}
