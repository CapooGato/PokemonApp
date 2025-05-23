package com.example.pokemonapp.api.repository;

import com.example.pokemonapp.api.models.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    List<Pokemon> findByType(String type);
}
