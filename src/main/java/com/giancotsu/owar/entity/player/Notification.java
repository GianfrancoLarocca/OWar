package com.giancotsu.owar.entity.player;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long senderId;
    private String sender;
    private String receiver;
    private LocalDateTime data;
    private String title;
    private String message;
    private String notificationType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    @JsonIgnore
    private PlayerEntity player;

    public Notification() {
    }

    public Notification(String sender, String receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public Notification(long senderId, String sender, String receiver, LocalDateTime data, String message, String notificationType, PlayerEntity player) {
        this.senderId = senderId;
        this.sender = sender;
        this.receiver = receiver;
        this.data = data;
        this.message = message;
        this.notificationType = notificationType;
        this.player = player;
    }

    public String getData() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return data.format(formatter);
    }
}
