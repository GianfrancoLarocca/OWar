package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.*;

@Entity
@Table(name = "player")
@Data
@ToString
public class PlayerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isOnline = false;
    private boolean isBanned = false;
    private boolean isVacationed = false;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private PlayerBasicInformationEntity basicInformation;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private RegistroAttivita registroAttivita;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private PlayerRisorse playerRisorse;

    @OneToOne(cascade = {CascadeType.ALL})
    @JsonManagedReference
    private Contatori contatori;

    @OneToMany(mappedBy = "player", cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<PlayerSviluppo> playerSviluppo;

    @OneToMany(mappedBy = "player", cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<Notification> notifications;

    @ManyToMany
    @JoinTable(
            name = "player_friends",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnore
    private Set<PlayerEntity> friends = new HashSet<>();
    private List<Long> sceltaRichiestaAmici = new ArrayList<>();

    public PlayerEntity() {
        this.playerRisorse = new PlayerRisorse();
        this.registroAttivita = new RegistroAttivita();
        this.contatori = new Contatori();
    }

    public void addFriend(PlayerEntity friend) {

        if(friend.getId() != this.id) {
            this.friends.add(friend);
            friend.getFriends().add(this);
        } else {
            throw new RuntimeException("Non puoi aggiungere te stesso agli amici!");
        }
    }

    public void removeFriend(PlayerEntity friend) {
        this.friends.remove(friend);
        friend.getFriends().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerEntity player = (PlayerEntity) o;

        return id.equals(player.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
