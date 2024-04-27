package com.giancotsu.owar.event.user;

import com.giancotsu.owar.entity.user.UserEntity;
import org.springframework.context.ApplicationEvent;

public class UserRegisteredEvent extends ApplicationEvent {

    private UserEntity user;
    public UserRegisteredEvent(Object source, UserEntity user) {
        super(source);
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
