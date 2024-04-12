package com.giancotsu.owar.dto.map;

import com.giancotsu.owar.dto.PlayerDto;
import com.giancotsu.owar.entity.player.Player;

public class PlayerMapper {

    private PlayerMapper() {
        throw new IllegalStateException("Utility class");
    }

    static Player mapToEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setNickname(playerDto.getNickname());
        return player;
    }

    static PlayerDto mapToDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setNickname(player.getNickname());
        return playerDto;
    }
}
