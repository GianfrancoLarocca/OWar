package com.giancotsu.owar.event.user;

import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredListener implements ApplicationListener<UserRegisteredEvent> {

    private final RisorseService risorseService;
    private final PlayerService playerService;

    public UserRegisteredListener(RisorseService risorseService, PlayerService playerService) {
        this.risorseService = risorseService;
        this.playerService = playerService;
    }

    @Override
    public void onApplicationEvent(UserRegisteredEvent event) {
        UserEntity user = event.getUser();
        risorseService.addNewPlayerResources(user);
        playerService.setNewActivity(user.getPlayer(), "Registrazione", "Ti sei registrato su OWAR");
    }
}
