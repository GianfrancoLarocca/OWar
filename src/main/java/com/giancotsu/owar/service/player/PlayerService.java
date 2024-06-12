package com.giancotsu.owar.service.player;

import com.giancotsu.owar.entity.player.Notification;
import com.giancotsu.owar.entity.player.FriendRequest;
import com.giancotsu.owar.dto.RisorsaDto;
import com.giancotsu.owar.dto.map.RisorseMapper;
import com.giancotsu.owar.entity.player.*;
import com.giancotsu.owar.entity.user.UserEntity;
import com.giancotsu.owar.event.strutture.SviluppoStruttureTryLvlUpFailEvent;
import com.giancotsu.owar.event.strutture.SviluppoStruttureTryLvlUpSuccessEvent;
import com.giancotsu.owar.event.sviluppo.SviluppoTryLvlUpFailEvent;
import com.giancotsu.owar.event.sviluppo.SviluppoTryLvlUpSuccessEvent;
import com.giancotsu.owar.event.tecnologia.TecnologiaTryLvlUpFailEvent;
import com.giancotsu.owar.event.tecnologia.TecnologiaTryLvlUpSuccessEvent;
import com.giancotsu.owar.repository.UserRepository;
import com.giancotsu.owar.repository.player.*;
import com.giancotsu.owar.security.JWTGenerator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BasicRepository basicRepository;
    private final PlayerRisorseRepository playerRisorseRepository;
    private final UserRepository userRepository;
    private final TecnologiaRepository tecnologiaRepository;
    private final StruttureRepository struttureRepository;
    private final AttivitaRepository attivitaRepository;
    private final CostiService costiService;
    private final JWTGenerator jwtGenerator;
    private final AlzaLivelloTry alzaLivelloTry;
    private final PlayerSviluppoRepository playerSviluppoRepository;
    private final PlayerStruttureRepository playerStruttureRepository;
    private final PlayerTecnologiaRepository playerTecnologiaRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final FriendRequestRepository friendRequestRepository;
    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PlayerService(PlayerRepository playerRepository, BasicRepository basicRepository, PlayerRisorseRepository playerRisorseRepository, UserRepository userRepository, TecnologiaRepository tecnologiaRepository, StruttureRepository struttureRepository, AttivitaRepository attivitaRepository, CostiService costiService, JWTGenerator jwtGenerator, AlzaLivelloTry alzaLivelloTry, PlayerSviluppoRepository playerSviluppoRepository, PlayerStruttureRepository playerStruttureRepository, PlayerTecnologiaRepository playerTecnologiaRepository, ApplicationEventPublisher eventPublisher, FriendRequestRepository friendRequestRepository, NotificationRepository notificationRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.playerRepository = playerRepository;
        this.basicRepository = basicRepository;
        this.playerRisorseRepository = playerRisorseRepository;
        this.userRepository = userRepository;
        this.tecnologiaRepository = tecnologiaRepository;
        this.struttureRepository = struttureRepository;
        this.attivitaRepository = attivitaRepository;
        this.costiService = costiService;
        this.jwtGenerator = jwtGenerator;
        this.alzaLivelloTry = alzaLivelloTry;
        this.playerSviluppoRepository = playerSviluppoRepository;
        this.playerStruttureRepository = playerStruttureRepository;
        this.playerTecnologiaRepository = playerTecnologiaRepository;
        this.eventPublisher = eventPublisher;
        this.friendRequestRepository = friendRequestRepository;
        this.notificationRepository = notificationRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public ResponseEntity<PlayerEntity> getPlayer(String bearerToken) {

        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());

        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformation(String bearerToken) {

        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (player.isPresent()) {
            return new ResponseEntity<>(player.get().getBasicInformation(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<PlayerEntity>> findPlayerByNickname(String nickname) {
        return new ResponseEntity<>(this.playerRepository.findPlayerByNickname(nickname), HttpStatus.OK);
    }

    public ResponseEntity<PlayerBasicInformationEntity> getPlayerBasicInformationByNickname(String nickname) {

        Optional<PlayerBasicInformationEntity> playerBasic = basicRepository.findByNickname(nickname);
        if (playerBasic.isPresent()) {
            return new ResponseEntity<>(playerBasic.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Boolean> sendFriendRequest(String bearerToken, long receiverId) {

        Optional<PlayerEntity> optionalSender = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        Optional<PlayerEntity> optionalReceiver = playerRepository.findById(receiverId);

        if(optionalSender.isPresent() && optionalReceiver.isPresent()) {

            PlayerEntity sender = optionalSender.get();
            String senderNickname = sender.getBasicInformation().getNickname();
            PlayerEntity receiver = optionalReceiver.get();

            Optional<List<FriendRequest>> fr = friendRequestRepository.alreadyExists(sender.getId(), receiverId);
            if(fr.isPresent() && (!fr.get().isEmpty())) {
                    return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }

            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setSender(sender.getId());
            friendRequest.setReceiver(receiverId);
            friendRequestRepository.save(friendRequest);

            String destination = "/topic/notification/" + receiverId;
            Notification not = new Notification(senderNickname, optionalReceiver.get().getBasicInformation().getNickname(), String.format("Hai una nuova richiesta d'amicizia da parte di %s", senderNickname));
            not.setData(LocalDateTime.now());
            not.setTitle("Nuova richiesta amico");
            not.setPlayer(receiver);
            not.setSenderId(sender.getId());
            not.setNotificationType("friend-request");
            simpMessagingTemplate.convertAndSend(destination, not);

            receiver.getContatori().incrementaContatoreNotifiche();
            playerRepository.save(receiver);

            List<Notification> notifications = new ArrayList<>(sender.getNotifications());
            notifications.add(not);
            sender.setNotifications(notifications);
            playerRepository.save(sender);

            return new ResponseEntity<>(true, HttpStatus.OK);
        }

        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Boolean> friendRequestChose(String bearerToken, long id) {
        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if(optionalPlayer.isPresent()) {
            PlayerEntity player = optionalPlayer.get();
            player.getSceltaRichiestaAmici().add(id);
            System.err.println("Sto aggiungendo un nuovo player alle scelte: " + player.getSceltaRichiestaAmici().get(0));
            playerRepository.save(player);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Boolean> addFriend(String bearerToken, long friendId) {

        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        Optional<PlayerEntity> friend = playerRepository.findById(friendId);
        if (player.isPresent() && friend.isPresent()) {

            PlayerEntity p = player.get();
            PlayerEntity f = friend.get();

            if(p.equals(f)) {
                return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
            }

            p.addFriend(f);

            String playerNickname = p.getBasicInformation().getNickname();
            String destination = "/topic/notification/" + friendId;
            Notification not = new Notification(playerNickname, f.getBasicInformation().getNickname(), String.format("%s Ha accettato la tua richiesta di amicizia! ", playerNickname));
            not.setData(LocalDateTime.now());
            not.setTitle("Hai nuovo amico");
            not.setPlayer(f);
            not.setSenderId(p.getId());
            not.setNotificationType("friend-request-accepted");
            simpMessagingTemplate.convertAndSend(destination, not);

            f.getContatori().incrementaContatoreNotifiche();
            playerRepository.save(f);

            List<Notification> notifications = new ArrayList<>(p.getNotifications());
            notifications.add(not);
            p.setNotifications(notifications);
            playerRepository.save(p);

            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Boolean> removeFriend(String bearerToken, long friendId) {

        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        Optional<PlayerEntity> friend = playerRepository.findById(friendId);

        if (player.isPresent()) {

            PlayerEntity p = player.get();
            PlayerEntity f = friend.get();

            p.removeFriend(f);
            playerRepository.save(p);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Set<PlayerEntity>> getFriends(String bearerToken) {
        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (player.isPresent()) {
            return new ResponseEntity<>(player.get().getFriends(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Long>> getFriendsIds(String bearerToken) {
        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (player.isPresent()) {
            return new ResponseEntity<>(playerRepository.friendsIds(player.get().getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Long>> getSentFriendRequests(String bearerToken) {
        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (player.isPresent()) {
            return new ResponseEntity<>(friendRequestRepository.getSentFriendRequest(player.get().getId()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<PlayerEntity>> getReceivedFriendRequests(String bearerToken) {
        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (player.isPresent()) {
            List<Long> senderIds = this.friendRequestRepository.getReceivedFriendRequest(player.get().getId());

            List<PlayerEntity> requestSenders = new ArrayList<>();
            senderIds.forEach(id -> {
                Optional<PlayerEntity> optionalPlayer = this.playerRepository.findById(id);
                optionalPlayer.ifPresent(requestSenders::add);
            });

            return new ResponseEntity<>(requestSenders, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Notification>> getNotifications(String bearerToken) {
        Optional<PlayerEntity> player = playerRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (player.isPresent()) {
            player.get().getContatori().setNotificationCounter(0);
            playerRepository.save(player.get());
            return new ResponseEntity<>(player.get().getNotifications(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Exp(n)=Base×(Livello×Fattore)
    //Exp(n) = Base * Math.pow(Fattore, livello - 1.0);
    public PlayerBasicInformationEntity increasePlayerLvl(PlayerEntity player, Double exp) {


        PlayerBasicInformationEntity basicInformation = player.getBasicInformation();
        PlayerEntity updatedPlayer;
        int livello = basicInformation.getLivello();
        Double experience = basicInformation.getExp();
        Double expBase = basicInformation.getExpBase();
        Double expFactor = basicInformation.getExpFactor();
        Double expNextLevel = basicInformation.getExpNextLevel();
        Double expTot = basicInformation.getExpTot();

        if (experience + exp >= expNextLevel) {
            while (experience + exp >= expNextLevel) {
                livello++;
                double expStartLvl = expBase * Math.pow(expFactor, livello - 1.0);
                basicInformation.setLivello(livello);
                expNextLevel = expBase * Math.pow(expFactor, livello);
                basicInformation.setExpStartLvl(expStartLvl);
                basicInformation.setExpNextLevel(expNextLevel);
                basicInformation.setExp((experience + exp) - expStartLvl);
            }
        } else {
            basicInformation.setExp(experience + exp);
        }

        basicInformation.setExpTot(expTot + exp);
        player.setBasicInformation(basicInformation);
        updatedPlayer = playerRepository.save(player);

        return updatedPlayer.getBasicInformation();

    }

    public ResponseEntity<List<Attivita>> getRegistroAttivita(String bearerToken) {

        List<Attivita> attivita = attivitaRepository.getAttivitaByPlayerId(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());

        return new ResponseEntity<>(attivita, HttpStatus.OK);

    }

    public ResponseEntity<Page<Attivita>> getRegistroAttivitaPageable(String bearerToken, int pageNumber, int pageSize) {

        long playerId = getUserFromAuthorizationToken(bearerToken).getPlayer().getId();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Attivita> attivita = attivitaRepository.getAttivitaByPlayerIdPageable(playerId, pageable);

        return new ResponseEntity<>(attivita, HttpStatus.OK);

    }

    public void setNewActivity(PlayerEntity player, String activity, String description) {
        Attivita attivita = new Attivita(activity, description);
        attivita.setRegistroAttivita(player.getRegistroAttivita());

        player.getRegistroAttivita().getAttivita().add(attivita);
        playerRepository.save(player);
    }

    @Deprecated
    public ResponseEntity<List<RisorsaDto>> getRisorsa(String bearerToken) {
        Optional<PlayerRisorse> risorsa = playerRisorseRepository.findById(getUserFromAuthorizationToken(bearerToken).getPlayer().getId());
        if (risorsa.isPresent()) {
            List<RisorsaDto> risorseDto = RisorseMapper.mapToDto(risorsa.get());
            return new ResponseEntity<>(risorseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> getChance(Long sviluppoId, String authorizationToken) {
        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(getUserFromAuthorizationToken(authorizationToken).getPlayer().getId());
        if (optionalPlayer.isPresent()) {
            PlayerEntity player = optionalPlayer.get();
            Optional<PlayerSviluppo> ops = playerSviluppoRepository.findByPlayerIdAndSviluppoId(player.getId(), sviluppoId);
            if (ops.isPresent()) {
                PlayerSviluppo ps = ops.get();
                if (player.getId() == ps.getPlayer().getId()) {
                    int livello = ps.getLivello();
                    String chance = String.format("%.0f", alzaLivelloTry.getPercentualeSuccesso(livello));
                    return new ResponseEntity<>(chance, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> provaAlzaLivello(Long sviluppoId, String authorizationToken) {

        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(getUserFromAuthorizationToken(authorizationToken).getPlayer().getId());
        if (optionalPlayer.isPresent()) {
            PlayerEntity player = optionalPlayer.get();
            Optional<PlayerSviluppo> ops = playerSviluppoRepository.findByPlayerIdAndSviluppoId(player.getId(), sviluppoId);
            if (ops.isPresent()) {
                PlayerSviluppo ps = ops.get();
                if (player.getId() == ps.getPlayer().getId()) {
                    if (!costiService.canPay(sviluppoId, authorizationToken)) {
                        return new ResponseEntity<>("No money", HttpStatus.BAD_REQUEST);
                    }
                    costiService.pay(sviluppoId, authorizationToken);
                    int livello = ps.getLivello();
                    boolean risultato = alzaLivelloTry.alzaLivello(livello);
                    //evento: tentativo di alzare il livello
                    if (risultato) {
                        ps.setLivello(livello + 1);
                        playerSviluppoRepository.save(ps);
                        Double exp = costiService.convertCostToExp(sviluppoId, authorizationToken);
                        eventPublisher.publishEvent(new SviluppoTryLvlUpSuccessEvent(this, player, ps, exp));
                        return new ResponseEntity<>("success", HttpStatus.OK);
                    } else {
                        eventPublisher.publishEvent(new SviluppoTryLvlUpFailEvent(this, player, ps));
                        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("building error", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("player error", HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<String> provaAlzaLivelloSviluppoStrutture(Long strutturaId, String authorizationToken) {

        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(getUserFromAuthorizationToken(authorizationToken).getPlayer().getId());
        if (optionalPlayer.isPresent()) {
            PlayerEntity player = optionalPlayer.get();
            Optional<PlayerStrutture> ops = playerStruttureRepository.findByPlayerIdAndSviluppoId(player.getId(), strutturaId);
            if (ops.isPresent()) {
                PlayerStrutture ps = ops.get();
                if (player.getId() == ps.getPlayer().getId()) {
                    if (!costiService.canPay(strutturaId, authorizationToken)) {
                        return new ResponseEntity<>("No money", HttpStatus.BAD_REQUEST);
                    }
                    costiService.paySviluppoStruttura(strutturaId, authorizationToken);
                    int livello = ps.getLivello();
                    boolean risultato = alzaLivelloTry.alzaLivello(livello);
                    //evento: tentativo di alzare il livello
                    if (risultato) {
                        ps.setLivello(livello + 1);
                        playerStruttureRepository.save(ps);
                        Double exp = costiService.convertCostToExpSviluppoStrutture(strutturaId, authorizationToken);
                        eventPublisher.publishEvent(new SviluppoStruttureTryLvlUpSuccessEvent(this, player, ps, exp));
                        return new ResponseEntity<>("success", HttpStatus.OK);
                    } else {
                        eventPublisher.publishEvent(new SviluppoStruttureTryLvlUpFailEvent(this, player, ps));
                        return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("building error", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("player error", HttpStatus.BAD_REQUEST);
        }

    }

    private boolean checkRequisitoTech(Long techId, Long playerId) {

        Optional<Integer> optReq = this.tecnologiaRepository.getRequisitoLvlByTechId(playerId, techId);
        int req;
        if(optReq.isPresent()) {
            req = optReq.get();
        } else {
            throw new RuntimeException("Requisito non trovato");
        }

        Optional<Integer> labLvlOpt = this.struttureRepository.getRequisitoLvl(playerId, "Laboratorio di ricerca");
        int labLvl;
        if (labLvlOpt.isPresent()) {
            labLvl = labLvlOpt.get();
        } else {
            throw new RuntimeException("Livello laboratorio non trovato");
        }

        return labLvl >= req;
    }

    public ResponseEntity<String> provaAlzaLivelloSviluppoTech(Long techId, String authorizationToken) {

        Optional<PlayerEntity> optionalPlayer = playerRepository.findById(getUserFromAuthorizationToken(authorizationToken).getPlayer().getId());
        if (optionalPlayer.isPresent()) {
            PlayerEntity player = optionalPlayer.get();
            if(this.checkRequisitoTech(techId, player.getId())) {
                Optional<PlayerTecnologia> opt = playerTecnologiaRepository.findByPlayerIdAndSviluppoId(player.getId(), techId);
                if (opt.isPresent()) {
                    PlayerTecnologia pt = opt.get();
                    if (player.getId() == pt.getPlayer().getId()) {
                        if (!costiService.canPayTech(techId, authorizationToken)) {
                            return new ResponseEntity<>("No money", HttpStatus.BAD_REQUEST);
                        }
                        costiService.paySviluppoTech(techId, authorizationToken);
                        int livello = pt.getLivello();
                        boolean risultato = alzaLivelloTry.alzaLivello(livello);
                        //evento: tentativo di alzare il livello
                        if (risultato) {
                            pt.setLivello(livello + 1);
                            playerTecnologiaRepository.save(pt);
                            Double exp = costiService.convertCostToExpNew(costiService.getCostiSviluppoTech(techId, player.getId()));
                            eventPublisher.publishEvent(new TecnologiaTryLvlUpSuccessEvent(this, player, pt, exp));
                            return new ResponseEntity<>("success", HttpStatus.OK);
                        } else {
                            eventPublisher.publishEvent(new TecnologiaTryLvlUpFailEvent(this, player, pt));
                            return new ResponseEntity<>("fail", HttpStatus.BAD_REQUEST);
                        }
                    } else {
                        return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("building error", HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("Non soddisfi i requisiti", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("player error", HttpStatus.BAD_REQUEST);
        }

    }

    private String getJWTFromHeaderRequest(String authorizationToken) {
        if (StringUtils.hasText(authorizationToken) && authorizationToken.startsWith("Bearer ")) {
            return authorizationToken.substring(7);
        } else {
            return null;
        }
    }

    private UserEntity getUserFromAuthorizationToken(String authorizationToken) {
        String jwt = this.getJWTFromHeaderRequest(authorizationToken);
        String username = jwtGenerator.extractUsername(jwt);
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }


}




























