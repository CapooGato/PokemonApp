package com.example.pokemonapp.api.controllers;

import com.example.pokemonapp.api.dto.pokemon.PokemonDto;
import com.example.pokemonapp.api.dto.pokemon.PokemonResponse;
import com.example.pokemonapp.api.dto.review.ReviewDto;
import com.example.pokemonapp.api.models.Pokemon;
import com.example.pokemonapp.api.models.Review;
import com.example.pokemonapp.api.service.impl.PokemonServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@WebMvcTest(controllers = PokemonController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class PokemonControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PokemonServiceImpl pokemonService;

    private Review review;
    private Pokemon pokemon;
    private ReviewDto reviewDto;
    private PokemonDto pokemonDto;
    @BeforeEach
    public void init() {
        review = Review.builder().title("title").content("content").stars(5).build();
        pokemon = Pokemon.builder().name("name pokemon").type("type pokemon").build();
        reviewDto = ReviewDto.builder().title("title").content("content").stars(1).build();
        pokemonDto = PokemonDto.builder().name("name").type("type").build();
    }

    @Test
    public void PokemonController_CreatePokemon_ReturnsCreated() throws Exception {
        given(pokemonService.createPokemon(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        ResultActions response = mockMvc.perform(post("/api/pokemon/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    public void PokemonController_GetAllPokemon_ReturnsPokemonResponse() throws Exception {
        int pageNo = 1;
        int pageSize = 10;

        PokemonResponse resultDto = PokemonResponse.builder()
                .content(Arrays.asList(pokemonDto))
                .pageNo(1).pageSize(10)
                .last(true).build();
        when(pokemonService.getALlPokemon(pageNo, pageSize)).thenReturn(resultDto);

        ResultActions response = mockMvc.perform(get("/api/pokemon")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNo", "1")
                .param("pageSize", "10"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.size()", CoreMatchers.is(resultDto.getContent().size())));
    }

    @Test
    public void PokemonController_PokemonDetail_ReturnsResponseDto() throws Exception {
        int pokemonId = 1;
        when(pokemonService.getPokemonById(pokemonId)).thenReturn(pokemonDto);
        ResultActions response = mockMvc.perform(get("/api/pokemon/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    public void PokemonController_UpdatePokemon_ReturnsResponseDto() throws Exception {
        int pokemonId = 1;
        when(pokemonService.updatePokemon(pokemonDto, pokemonId)).thenReturn(pokemonDto);
        ResultActions response = mockMvc.perform(put("/api/pokemon/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pokemonDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(pokemonDto.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(pokemonDto.getType())));
    }

    @Test
    public void PokemonController_DeletePokemon_ReturnsResponseDto() throws Exception {
        int pokemonId = 1;
        doNothing().when(pokemonService).deletePokemonById(pokemonId);

        ResultActions response = mockMvc.perform(delete("/api/pokemon/1/delete"));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }
}
