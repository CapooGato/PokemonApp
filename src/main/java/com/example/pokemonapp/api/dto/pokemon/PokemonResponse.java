package com.example.pokemonapp.api.dto.pokemon;

import lombok.Data;

import java.util.List;

@Data
public class PokemonResponse {
    private List<PokemonDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElemets;
    private int totalPages;
    private boolean last;
}
