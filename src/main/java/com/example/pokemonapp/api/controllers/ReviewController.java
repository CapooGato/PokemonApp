package com.example.pokemonapp.api.controllers;

import com.example.pokemonapp.api.dto.review.ReviewDto;
import com.example.pokemonapp.api.dto.review.ReviewResponse;
import com.example.pokemonapp.api.service.impl.ReviewServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class ReviewController {

    private final ReviewServiceImpl reviewService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping(value = "pokemon/{pokemonId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId,
                                                  @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto newReview = reviewService.createReview(pokemonId, reviewDto);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @GetMapping(value = "review")
    public ResponseEntity<ReviewResponse> getAllReviews(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "1", required = false) int pageSize
    ){
        ReviewResponse response = reviewService.getAllReviews(pageNo, pageSize);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "pokemon/{pokemonId}/reviews")
    public ResponseEntity<List<ReviewDto>> getPokemonReviews(@PathVariable(value = "pokemonId") int pokemonId){
        List<ReviewDto> pokemonReviews = reviewService.getReviewsByPokemonId(pokemonId);
        return new ResponseEntity<>(pokemonReviews, HttpStatus.OK);
    }

    @GetMapping(value = "pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewByPokemon(@PathVariable(value = "pokemonId") int pokemonId,
                                                        @PathVariable(value = "id") int reviewId){
        ReviewDto reviewDto = reviewService.getReviewById(reviewId, pokemonId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping(value = "pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") int pokemonId,
                                                  @PathVariable(value = "id") int reviewId,
                                                  @Valid @RequestBody ReviewDto reviewDto) {
        ReviewDto updatedReview = reviewService.updateReview(reviewId, pokemonId, reviewDto);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    @DeleteMapping(value = "pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId,
                                               @PathVariable(value = "id") int reviewId) {
        reviewService.deleteReview(reviewId, pokemonId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
