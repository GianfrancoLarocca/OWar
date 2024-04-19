package com.giancotsu.owar.dto;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SviluppoCompletoDto {


    String nome;
    String descrizione;
    Integer livello;
    String urlImmagine;
    Map<String, Long> costi;
    Map<String, Long> crescita;

}
