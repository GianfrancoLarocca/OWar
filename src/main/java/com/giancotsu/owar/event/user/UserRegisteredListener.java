package com.giancotsu.owar.event.user;

import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredListener implements ApplicationListener<UserRegisteredEvent> {

    private final RisorseService risorseService;

    public UserRegisteredListener(RisorseService risorseService) {
        this.risorseService = risorseService;
    }

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        UserEntity user = event.getUser();
        risorseService.addNewPlayerResources(user);
    }
}
