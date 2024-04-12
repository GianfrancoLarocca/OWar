package com.giancotsu.owar.entity.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    @Size(min=4, max=20, message = "L'Username deve essere tra 4 e 20 caratteri")
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Email non valida")
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min=8, max=100, message = "La Password deve essere tra 8 e 100 caratteri")
    private String password;

    @Column(name = "is-active", nullable = false)
    private boolean isActive;

    @Column(name = "activation-code", nullable = false)
    private String activationCode;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reset-password-id", referencedColumnName = "id")
    private UserResetPasswordEntity resetPasswordEntity;

}
