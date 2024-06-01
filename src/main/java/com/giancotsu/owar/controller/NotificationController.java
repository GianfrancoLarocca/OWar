package com.giancotsu.owar.controller;

import com.giancotsu.owar.entity.player.Notification;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {


    @MessageMapping("/friend-request/{receiverId}")
    @SendTo("/topic/notification/{receiverId}")
    public Notification friendRequestNotification(@DestinationVariable("receiverId") long receiverId, Notification notification) {
        return notification;
    }
}
