package com.giancotsu.owar.dto;

import lombok.Data;

@Data
public class ClassificaDto {

    private Long id;
    private String nickname;
    private int livello;
    private long cp;
    private String img;
    private int cambioPosizione;
}
