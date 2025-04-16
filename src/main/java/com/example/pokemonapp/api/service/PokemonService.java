package com.example.pokemonapp.api.service;

import com.example.pokemonapp.api.dto.pokemon.PokemonDto;
import com.example.pokemonapp.api.dto.pokemon.PokemonResponse;
import org.springframework.stereotype.Service;

@Service
public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getALlPokemon(int pageNo, int pageSize);
    PokemonDto getPokemonById(int id);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);
    void deletePokemonById(int id);
}
