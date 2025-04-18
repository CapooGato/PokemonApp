package com.example.pokemonapp.api.security;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 3600000; // 1h in miliseconds
    public static final String JWT_SECRET = "secret"; // should be longer, 32 chars for 256 b algorytms
}
