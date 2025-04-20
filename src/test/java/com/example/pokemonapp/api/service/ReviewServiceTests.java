package com.example.pokemonapp.api.service;

import com.example.pokemonapp.api.dto.pokemon.PokemonDto;
import com.example.pokemonapp.api.dto.review.ReviewDto;
import com.example.pokemonapp.api.models.Pokemon;
import com.example.pokemonapp.api.models.Review;
import com.example.pokemonapp.api.repository.PokemonRepository;
import com.example.pokemonapp.api.repository.ReviewRepository;
import com.example.pokemonapp.api.service.impl.ReviewServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private PokemonRepository pokemonRepository;
    @InjectMocks
    private ReviewServiceImpl reviewService;

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
    public void ReviewService_CreateReview_ReturnsReviewDto() {
        when(pokemonRepository.findById(pokemon.getId())).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        ReviewDto savedReviewDto = reviewService.createReview(pokemon.getId(), reviewDto);

        Assertions.assertThat(savedReviewDto).isNotNull();
    }

    @Test
    public void ReviewService_GetReviewsByPokemonId_ReturnsListOfReviewDto() {
        when(pokemonRepository.existsById(pokemon.getId())).thenReturn(true);
        when(reviewRepository.findByPokemonId(pokemon.getId())).thenReturn(Arrays.asList(review));

        List<ReviewDto> savedReviewsDto = reviewService.getReviewsByPokemonId(pokemon.getId());

        Assertions.assertThat(savedReviewsDto).isNotNull();
        Assertions.assertThat(savedReviewsDto.size()).isEqualTo(1);
    }

    @Test
    public void ReviewService_GetReviewById_ReturnsReviewDto() {
        int reviewId = 1;
        int pokemonId = 1;

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));

        review.setPokemon(pokemon);

        ReviewDto result = reviewService.getReviewById(reviewId, pokemonId);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void ReviewService_UpdateReview_ReturnsReviewDto() {
        int reviewId = 1;
        int pokemonId = 1;

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));
        when(reviewRepository.save(Mockito.any(Review.class))).thenReturn(review);

        review.setPokemon(pokemon);

        ReviewDto result = reviewService.updateReview(reviewId, pokemonId, reviewDto);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void ReviewService_DeleteReview_ShouldDeleteReview() {
        int reviewId = 1;
        int pokemonId = 1;

        when(pokemonRepository.findById(pokemonId)).thenReturn(Optional.ofNullable(pokemon));
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.ofNullable(review));
        review.setPokemon(pokemon);

        assertDoesNotThrow(() -> reviewService.deleteReview(reviewId, pokemonId));
    }
}
