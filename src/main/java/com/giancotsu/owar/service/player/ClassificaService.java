package com.giancotsu.owar.service.player;

import com.giancotsu.owar.dto.ClassificaDto;
import com.giancotsu.owar.projection.ClassificaProjection;
import com.giancotsu.owar.repository.player.ClassificaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ClassificaService {

    private final ClassificaRepository classificaRepository;

    // CLASSIFICA LIVELLO
    private Map<Long, ClassificaDto> classificaLvlMap = new LinkedHashMap<>();
    private Map<Long, Integer> posizioneLvl = new HashMap<>();
    private Map<Long, Integer> cambioPosizioneLvl = new HashMap<>();

    // CLASSIFICA CP
    private Map<Long, ClassificaDto> classificaCpMap = new LinkedHashMap<>();
    private Map<Long, Integer> posizioneCp = new HashMap<>();
    private Map<Long, Integer> cambioPosizioneCp = new HashMap<>();

    private final int UPDATE_TIME_LVL = 1000 * 60 * 2;
    private final int UPDATE_TIME_CP = 1000 * 60 * 3;


    public ClassificaService(ClassificaRepository classificaRepository) {
        this.classificaRepository = classificaRepository;

        this.extractAllClassifica();
    }

    public void extractAllClassifica() {
        this.extractClassificaLvlMap();
        this.extractClassificaCpMap();
    }

    private void extractClassificaLvlMap() {

        List<ClassificaProjection> classificaLivelloProj = classificaRepository.getClassificaLvl();

        AtomicInteger index = new AtomicInteger(0);
        classificaLivelloProj.forEach(classifica -> {

            ClassificaDto classificaLvlDto = new ClassificaDto();

            classificaLvlDto.setId(classifica.getId());
            classificaLvlDto.setNickname(classifica.getNickname());
            classificaLvlDto.setLivello(classifica.getLivello());
            classificaLvlDto.setCp(classifica.getCp());
            classificaLvlDto.setImg(classifica.getImg());

            if(posizioneLvl.get(classifica.getId()) == null) {
                posizioneLvl.put(classifica.getId(), index.get()+1);
            }

            index.getAndIncrement();

            this.classificaLvlMap.put(classifica.getId(), classificaLvlDto);
        });
    }

    private void extractClassificaCpMap() {

        List<ClassificaProjection> classificaCpProj = classificaRepository.getClassificaCp();

        AtomicInteger index = new AtomicInteger(0);
        classificaCpProj.forEach(classifica -> {

            ClassificaDto classificaCpDto = new ClassificaDto();

            classificaCpDto.setId(classifica.getId());
            classificaCpDto.setNickname(classifica.getNickname());
            classificaCpDto.setLivello(classifica.getLivello());
            classificaCpDto.setCp(classifica.getCp());
            classificaCpDto.setImg(classifica.getImg());

            if(posizioneCp.get(classifica.getId()) == null) {
                posizioneCp.put(classifica.getId(), index.get()+1);
            }

            index.getAndIncrement();

            this.classificaCpMap.put(classifica.getId(), classificaCpDto);
        });
    }

    @Scheduled(fixedDelay = UPDATE_TIME_LVL)
    private void updateClassificaLvl() {

        System.err.println( LocalTime.now() +" updateClassificaLvl");

        if(!this.classificaLvlMap.isEmpty()) {

            Map<Long, ClassificaDto> newClassifica = new LinkedHashMap<>();

            List<ClassificaProjection> classificaLivelloProj = classificaRepository.getClassificaLvl();

            AtomicInteger index = new AtomicInteger(0);

            classificaLivelloProj.forEach(classifica -> {
                ClassificaDto classificaLvlDto = new ClassificaDto();

                long playerId = classifica.getId();

                classificaLvlDto.setId(playerId);
                classificaLvlDto.setNickname(classifica.getNickname());
                classificaLvlDto.setLivello(classifica.getLivello());
                classificaLvlDto.setCp(classifica.getCp());
                classificaLvlDto.setImg(classifica.getImg());

                int posizioneLvlInt = this.posizioneLvl.get(playerId);
                if(posizioneLvlInt != index.get()+1) {

                    if(this.cambioPosizioneLvl.get(playerId) != null) {
                        classificaLvlDto.setCambioPosizione(posizioneLvlInt - (index.get() + 1) + this.cambioPosizioneLvl.get(playerId));
                        this.cambioPosizioneLvl.put(playerId, posizioneLvlInt - (index.get() + 1) + this.cambioPosizioneLvl.get(playerId));
                    } else {
                        classificaLvlDto.setCambioPosizione(posizioneLvlInt - (index.get() + 1));
                        this.cambioPosizioneLvl.put(playerId, posizioneLvlInt - (index.get() + 1));
                    }
                            this.posizioneLvl.put(playerId, index.get()+1);
                } else {
                    if(this.cambioPosizioneLvl.get(playerId) != null) {
                        classificaLvlDto.setCambioPosizione(this.cambioPosizioneLvl.get(playerId));
                    } else {
                        classificaLvlDto.setCambioPosizione(0);
                    }
                }


                index.getAndIncrement();

                newClassifica.put(playerId, classificaLvlDto);
            });
            this.classificaLvlMap = newClassifica;
        }
    }

    @Scheduled(fixedDelay = UPDATE_TIME_CP)
    private void updateClassificaCp() {

        System.err.println( LocalTime.now() +" updateClassificaCp");

        if(!this.classificaCpMap.isEmpty()) {

            Map<Long, ClassificaDto> newClassifica = new LinkedHashMap<>();

            List<ClassificaProjection> classificaCpProj = classificaRepository.getClassificaCp();

            AtomicInteger index = new AtomicInteger(0);

            classificaCpProj.forEach(classifica -> {
                ClassificaDto classificaCpDto = new ClassificaDto();

                long playerId = classifica.getId();

                classificaCpDto.setId(playerId);
                classificaCpDto.setNickname(classifica.getNickname());
                classificaCpDto.setLivello(classifica.getLivello());
                classificaCpDto.setCp(classifica.getCp());
                classificaCpDto.setImg(classifica.getImg());

                int posizioneCpInt = this.posizioneCp.get(playerId);
                if(posizioneCpInt != index.get()+1) {

                    if(this.cambioPosizioneCp.get(playerId) != null) {
                        classificaCpDto.setCambioPosizione(posizioneCpInt - (index.get() + 1) + this.cambioPosizioneCp.get(playerId));
                        this.cambioPosizioneCp.put(playerId, posizioneCpInt - (index.get() + 1) + this.cambioPosizioneCp.get(playerId));
                    } else {
                        classificaCpDto.setCambioPosizione(posizioneCpInt - (index.get() + 1));
                        this.cambioPosizioneCp.put(playerId, posizioneCpInt - (index.get() + 1));
                    }
                    this.posizioneCp.put(playerId, index.get()+1);
                } else {
                    if(this.cambioPosizioneCp.get(playerId) != null) {
                        classificaCpDto.setCambioPosizione(this.cambioPosizioneCp.get(playerId));
                    } else {
                        classificaCpDto.setCambioPosizione(0);
                    }
                }


                index.getAndIncrement();

                newClassifica.put(playerId, classificaCpDto);
            });
            this.classificaCpMap = newClassifica;
        }
    }

    public Collection<ClassificaDto> getClassificaLivello() {
        return this.classificaLvlMap.values();
    }
    public Collection<ClassificaDto> getClassificaCp() {
        return this.classificaCpMap.values();
    }

    @Scheduled(cron = "0 0 5 * * *")
    private void resetCambioPosizioneLvl() {
        this.posizioneLvl.keySet().forEach(playerId -> {
            this.cambioPosizioneLvl.put(playerId, 0);
            this.cambioPosizioneCp.put(playerId, 0);
        });
        this.extractAllClassifica();
    }
}












































