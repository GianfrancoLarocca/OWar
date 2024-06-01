package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.*;
import com.giancotsu.owar.entity.player.*;
import com.giancotsu.owar.service.player.CostiService;
import com.giancotsu.owar.service.player.PlayerService;
import com.giancotsu.owar.service.player.PlayerSviluppoService;
import com.giancotsu.owar.service.player.RisorseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "api/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerSviluppoService playerSviluppoService;
    private final RisorseService risorseService;
    private final CostiService costiService;
    private final SimpMessagingTemplate simpMessagingTemplate;



    public PlayerController(PlayerService playerService, PlayerSviluppoService playerSviluppoService, RisorseService risorseService, CostiService costiService, SimpMessagingTemplate simpMessagingTemplate) {
        this.playerService = playerService;
        this.playerSviluppoService = playerSviluppoService;
        this.risorseService = risorseService;
        this.costiService = costiService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping()
    public ResponseEntity<PlayerEntity> getPlayer(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getPlayer(bearerToken);
    }

    @GetMapping(value = "basic")
    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformation(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getPlayerBasicInformation(bearerToken);
    }

    @GetMapping(value = "find/{nickname}")
    public ResponseEntity<List<PlayerEntity>> findPlayerByNickname(@PathVariable("nickname") String nickname) {
        return this.playerService.findPlayerByNickname(nickname);
    }

    @GetMapping(value = "/send-friend-request/{receiverId}")
    public ResponseEntity<Boolean> sendFriendRequest(@RequestHeader("Authorization") String bearerToken, @PathVariable("receiverId") long receiverId) {
        return this.playerService.sendFriendRequest(bearerToken, receiverId);
    }

    @GetMapping(value = "/friend-request-chose/{id}")
    public ResponseEntity<Boolean> friendRequestChose(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") long id) {
        return this.playerService.friendRequestChose(bearerToken, id);
    }

    @PostMapping(value = "friend/add/{friendId}")
    public ResponseEntity<Boolean> addFriend(@RequestHeader("Authorization") String bearerToken, @PathVariable("friendId") long friendId) {
        return this.playerService.addFriend(bearerToken, friendId);
    }

    @PostMapping(value = "friend/remove/{friendId}")
    public ResponseEntity<Boolean> removeFriend(@RequestHeader("Authorization") String bearerToken, @PathVariable("friendId") long friendId) {
        return this.playerService.removeFriend(bearerToken, friendId);
    }

    @GetMapping(value = "friends")
    public ResponseEntity<Set<PlayerEntity>> getFriends(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getFriends(bearerToken);
    }

    @GetMapping(value = "friends/ids")
    public ResponseEntity<List<Long>> getFriendsIds(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getFriendsIds(bearerToken);
    }

    @GetMapping(value = "friend-requests/ids")
    public ResponseEntity<List<Long>> getSentFriendRequests(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getSentFriendRequests(bearerToken);
    }

    @GetMapping(value = "received-friend-requests")
    public ResponseEntity<List<PlayerEntity>> getReceivedFriendRequests(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getReceivedFriendRequests(bearerToken);
    }

    @GetMapping(value = "notifications")
    public ResponseEntity<List<Notification>> getNotifications(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getNotifications(bearerToken);
    }

    @GetMapping(value = "basic/{nickname}")
    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformationByNickname(@PathVariable("nickname") String nickname) {
        return this.playerService.getPlayerBasicInformationByNickname(nickname);
    }

    @GetMapping(value = "registro-attivita")
    public ResponseEntity<List<Attivita>> getRegistroAttivita(@RequestHeader("Authorization") String bearerToken) {
        return this.playerService.getRegistroAttivita(bearerToken);
    }

    @GetMapping(value = "registro-attivita/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<Attivita>> getRegistroAttivitaPageable(@RequestHeader("Authorization") String bearerToken, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        return this.playerService.getRegistroAttivitaPageable(bearerToken, pageNumber, pageSize);
    }

    @GetMapping(value = "risorse")
    public ResponseEntity<List<RisorsaDto>> getPlayerRisorse(@RequestHeader("Authorization") String bearerToken) {
        return this.risorseService.getPlayerResourcesDto(bearerToken);
    }

    @GetMapping(value = "strutture")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfo(@RequestHeader("Authorization") String bearerToken) {
        return this.playerSviluppoService.getAllBasicBuildingsInfo(bearerToken);
    }

    @GetMapping(value = "strutture/{nickname}")
    public ResponseEntity<List<AllBasicBuildingsInfoDto>> getAllBasicBuildingsInfoByPlayerNickname(@PathVariable("nickname") String nickname) {
        return this.playerSviluppoService.getAllBasicBuildingsInfoByPlayerNickname(nickname);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}")
    public ResponseEntity<SviluppoCompletoDto> getSviluppoDettById(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerSviluppoService.getSviluppoDettById(sviluppoId, bearerToken);
    }

    @GetMapping(value = "strutture/nome/{nome}")
    public ResponseEntity<SviluppoCompletoDto> getSviluppoDettByName(@RequestHeader("Authorization") String bearerToken, @PathVariable("nome") String nomeStruttura) {
        return this.playerSviluppoService.getSviluppoDettByName(nomeStruttura, bearerToken);
    }

    @GetMapping(value = "strutture/produzione")
    public ResponseEntity<List<ProduzioneRisorseDto>> getProduzione(@RequestHeader("Authorization") String bearerToken) {
        return this.risorseService.getProductionResourcesDto(bearerToken);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/alzalivello")
    public ResponseEntity<String> provaAlzaLivello(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerService.provaAlzaLivello(sviluppoId, bearerToken);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/chance")
    public ResponseEntity<String> getChance(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {
        return this.playerService.getChance(sviluppoId, bearerToken);
    }

    @GetMapping(value = "strutture/id/{sviluppoId}/canpay")
    public ResponseEntity<Boolean> canPay(@RequestHeader("Authorization") String bearerToken, @PathVariable("sviluppoId") Long sviluppoId) {

        boolean canPay = this.costiService.canPay(sviluppoId, bearerToken);
        if(canPay) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}























