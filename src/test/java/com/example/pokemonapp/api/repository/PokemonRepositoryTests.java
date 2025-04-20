package com.example.pokemonapp.api.repository;

import com.example.pokemonapp.api.models.Pokemon;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

/**
 * Testujemy tylko metody które są przez nas używane, nawet jeśli są provided do JPARepository
 * */
@DataJpaTest    // tells spring that there are tests
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // h2 db to test
public class PokemonRepositoryTests {


    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonRepositoryTests(PokemonRepository pokemonRepository){
        this.pokemonRepository = pokemonRepository;
    }

    @Test
    public void PokemonRepository_Save_ReturnsSavedPokemom() {

        // Arrange
        Pokemon pokemon = Pokemon.builder()
                .name("Pikachu")
                .type("electric")
                .build();

        // Act
        Pokemon savedPokemon = pokemonRepository.save(pokemon);

        // Assert
        Assertions.assertThat(savedPokemon).isNotNull();
        Assertions.assertThat(savedPokemon.getId()).isGreaterThan(0);
    }

    @Test
    public void PokemonRepository_FindAll_ReturnsMoreThanOnePokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("electrical").build();
        Pokemon pokemon2 = Pokemon.builder().name("Charmander").type("fire").build();

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> pokemonList = pokemonRepository.findAll();

        Assertions.assertThat(pokemonList).isNotNull();
        Assertions.assertThat(pokemonList.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_FindById_ReturnsMoreThanOnePokemon() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("electrical").build(); // Arrange

        pokemonRepository.save(pokemon);

        Pokemon foundPokemon = pokemonRepository.findById(pokemon.getId()).get(); // Act

        Assertions.assertThat(foundPokemon).isNotNull(); // Assert
    }

    @Test
    public void PokemonRepository_FindByType_ReturnsMoreThanOnePokemonNotNull() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("electrical").build(); // Arrange
        Pokemon pokemon2 = Pokemon.builder().name("Raichu").type("electrical").build(); // Arrange

        pokemonRepository.save(pokemon);
        pokemonRepository.save(pokemon2);

        List<Pokemon> foundPokemons = pokemonRepository.findByType(pokemon.getType()); // Act

        Assertions.assertThat(foundPokemons).isNotNull(); // Assert
        Assertions.assertThat(foundPokemons.size()).isEqualTo(2);
    }

    @Test
    public void PokemonRepository_UpdatePokemon_ReturnsMoreThanOnePokemonNotNull() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("electrical").build(); // Arrange

        pokemonRepository.save(pokemon);

        Pokemon updatedPokemon = pokemonRepository.findById(pokemon.getId()).get();
        updatedPokemon.setName("Raichu");
        updatedPokemon.setType("Electrical");

        Pokemon savedPokemon = pokemonRepository.save(updatedPokemon);

        Assertions.assertThat(savedPokemon.getType()).isNotNull(); // Assert
        Assertions.assertThat(savedPokemon.getName()).isNotNull();
    }

    @Test
    public void PokemonRepository_DeletePokemon_ReturnsPokemonIsEmpty() {
        Pokemon pokemon = Pokemon.builder().name("Pikachu").type("electrical").build();

        pokemonRepository.save(pokemon);

        pokemonRepository.delete(pokemon);

        Optional<Pokemon> deletedPokemon = pokemonRepository.findById(pokemon.getId());

        Assertions.assertThat(deletedPokemon).isEmpty();
    }
}
