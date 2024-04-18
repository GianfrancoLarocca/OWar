package com.giancotsu.owar.dto.map;

import com.giancotsu.owar.dto.PlayerDto;
import com.giancotsu.owar.entity.player.PlayerEntity;

public class PlayerMapper {

    private PlayerMapper() {
        throw new IllegalStateException("Utility class");
    }

//    static PlayerEntity mapToEntity(PlayerDto playerDto) {
//        PlayerEntity player = new PlayerEntity();
//        player.setId(playerDto.getId());
//        player.setNickname(playerDto.getNickname());
//        return player;
//    }
//
//    static PlayerDto mapToDto(PlayerEntity player) {
//        PlayerDto playerDto = new PlayerDto();
//        playerDto.setId(player.getId());
//        playerDto.setNickname(player.getNickname());
//        return playerDto;
//    }
}
