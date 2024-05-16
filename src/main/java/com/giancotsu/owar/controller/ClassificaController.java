package com.giancotsu.owar.controller;

import com.giancotsu.owar.dto.ClassificaDto;
import com.giancotsu.owar.service.player.ClassificaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/classifica")
public class ClassificaController {

    private final ClassificaService classificaService;

    public ClassificaController(ClassificaService classificaService) {
        this.classificaService = classificaService;
    }

    @GetMapping(value = "livello")
    public ResponseEntity<Collection<ClassificaDto>> classificaLivello() {

        return new ResponseEntity<>(classificaService.getClassificaLivello(), HttpStatus.OK);
    }

    @GetMapping(value = "cp")
    public ResponseEntity<Collection<ClassificaDto>> classificaCp() {

        return new ResponseEntity<>(classificaService.getClassificaCp(), HttpStatus.OK);
    }
}
