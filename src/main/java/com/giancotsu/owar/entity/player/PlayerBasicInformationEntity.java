package com.giancotsu.owar.entity.player;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "basic-information")
@Data
@NoArgsConstructor
@ToString
public class PlayerBasicInformationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname",nullable = false, unique = true)
    private String nickname;

    @Column(name = "lvl", nullable = false)
    private int livello = 0;

    public PlayerBasicInformationEntity(String nickname) {
        this.nickname = nickname;
    }
}
