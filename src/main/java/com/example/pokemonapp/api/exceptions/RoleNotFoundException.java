package com.example.pokemonapp.api.exceptions;


public class RoleNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 2;

    public RoleNotFoundException(String messasge){
        super(messasge);
    }
}
