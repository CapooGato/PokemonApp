package com.example.pokemonapp.api.service;

import com.example.pokemonapp.api.dto.pokemon.PokemonDto;
import com.example.pokemonapp.api.dto.pokemon.PokemonResponse;
import com.example.pokemonapp.api.models.Pokemon;
import com.example.pokemonapp.api.repository.PokemonRepository;
import com.example.pokemonapp.api.service.impl.PokemonServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @InjectMocks
    private PokemonServiceImpl pokemonService;


    @Test
    public void PokemonService_CreatePokemon_ReturnsPokemonDto() {
        Pokemon pokemon = Pokemon.builder().name("name pokemon").type("type pokemon").build();
        PokemonDto pokemonDto = PokemonDto.builder().name("name").type("type").build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);

        PokemonDto savedPokemonDto = pokemonService.createPokemon(pokemonDto);

        Assertions.assertThat(savedPokemonDto).isNotNull();
    }

    @Test
    public void PokemonService_GetAllPokemon_ReturnsResponseDto() {
        Page<Pokemon> pokemons = Mockito.mock(Page.class);

        // gdy ktoś wywoła pokemonRepository.findAll(), z argumentem typu Pageable to zwracamy pokemons
        when(pokemonRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pokemons);

        PokemonResponse savePokemon = pokemonService.getALlPokemon(1,10);

        Assertions.assertThat(savePokemon).isNotNull();
    }

    @Test
    public void PokemonService_GetPokemonById_ReturnsPokemonDto() {
        Pokemon pokemon = Pokemon.builder().name("name pokemon").type("type pokemon").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto foundPokemonDto = pokemonService.getPokemonById(1);

        Assertions.assertThat(foundPokemonDto).isNotNull();
    }

    @Test
    public void PokemonService_UpdatePokemon_ReturnsPokemonDto() {
        Pokemon pokemon = Pokemon.builder().name("name pokemon").type("type pokemon").build();

        when(pokemonRepository.save(Mockito.any(Pokemon.class))).thenReturn(pokemon);
        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        PokemonDto newPokemonData = PokemonDto.builder().name("new name").type("new type").build();
        PokemonDto updatedPokemon = pokemonService.updatePokemon(newPokemonData, 1);

        Assertions.assertThat(updatedPokemon).isNotNull();
        Assertions.assertThat(updatedPokemon.getName()).isNotNull();
        Assertions.assertThat(updatedPokemon.getType()).isNotNull();
    }

    @Test
    public void PokemonService_DeletePokemon_ShouldDeleteSuccessfully() {
        Pokemon pokemon = Pokemon.builder().name("name pokemon").type("type pokemon").build();

        when(pokemonRepository.findById(1)).thenReturn(Optional.ofNullable(pokemon));

        assertDoesNotThrow(() -> pokemonService.deletePokemonById(1));
    }

}
