package com.giancotsu.owar.dto.map;

import com.giancotsu.owar.dto.RisorsaDto;
import com.giancotsu.owar.entity.player.PlayerRisorse;

import java.util.ArrayList;
import java.util.List;

public class RisorseMapper {

    private RisorseMapper() {
        throw new RuntimeException("Util class");
    }

    public static List<RisorsaDto> mapToDto(PlayerRisorse pr) {

        List<RisorsaDto> risorseDto = new ArrayList<>();

        RisorsaDto metalloDto = new RisorsaDto();
        metalloDto.setNomeRisorsa(pr.getMetallo().getNome());
        metalloDto.setQuantitaCorta(pr.getMetallo().valueShorter());
        metalloDto.setQuantitaLunga(pr.getMetallo().value());
        metalloDto.setUrlImmagine(pr.getMetallo().getUrlImmagine());
        risorseDto.add(metalloDto);

        RisorsaDto microchipDto = new RisorsaDto();
        microchipDto.setNomeRisorsa(pr.getMicrochip().getNome());
        microchipDto.setQuantitaCorta(pr.getMicrochip().valueShorter());
        microchipDto.setQuantitaLunga(pr.getMicrochip().value());
        microchipDto.setUrlImmagine(pr.getMicrochip().getUrlImmagine());
        risorseDto.add(microchipDto);

        RisorsaDto energiaDto = new RisorsaDto();
        energiaDto.setNomeRisorsa(pr.getEnergia().getNome());
        energiaDto.setQuantitaCorta(pr.getEnergia().valueShorter());
        energiaDto.setQuantitaLunga(pr.getEnergia().value());
        energiaDto.setUrlImmagine(pr.getEnergia().getUrlImmagine());
        risorseDto.add(energiaDto);

        RisorsaDto civiliDto = new RisorsaDto();
        civiliDto.setNomeRisorsa(pr.getCivili().getNome());
        civiliDto.setQuantitaCorta(pr.getCivili().valueShorter());
        civiliDto.setQuantitaLunga(pr.getCivili().value());
        civiliDto.setUrlImmagine(pr.getCivili().getUrlImmagine());
        risorseDto.add(civiliDto);

        RisorsaDto bitcoinDto = new RisorsaDto();
        bitcoinDto.setNomeRisorsa(pr.getBitcoin().getNome());
        bitcoinDto.setQuantitaCorta(pr.getBitcoin().valueShorter());
        bitcoinDto.setQuantitaLunga(pr.getBitcoin().value());
        bitcoinDto.setUrlImmagine(pr.getBitcoin().getUrlImmagine());
        risorseDto.add(bitcoinDto);

        RisorsaDto acquaDto = new RisorsaDto();
        acquaDto.setNomeRisorsa(pr.getAcqua().getNome());
        acquaDto.setQuantitaCorta(pr.getAcqua().valueShorter());
        acquaDto.setQuantitaLunga(pr.getAcqua().value());
        acquaDto.setUrlImmagine(pr.getAcqua().getUrlImmagine());
        risorseDto.add(acquaDto);

        return risorseDto;
    }
}
