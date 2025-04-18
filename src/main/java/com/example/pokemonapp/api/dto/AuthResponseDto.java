package com.example.pokemonapp.api.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;
    private String tokenType = "Barer ";

    public AuthResponseDto(String accessToken){
        this.accessToken = accessToken;
    }
}
