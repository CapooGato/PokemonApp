package com.example.pokemonapp.api.service;

import com.example.pokemonapp.api.dto.review.ReviewDto;
import com.example.pokemonapp.api.dto.review.ReviewResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);
    ReviewDto findReviewDetail(int reviewId);
    List<ReviewDto> getReviewsByPokemonId(int pokemonId);
    ReviewResponse getAllReviews(int pageNo, int pageSize);
    ReviewDto getReviewById(int reviewId, int pokemonId);
    ReviewDto updateReview(int reviewId, int pokemonId, ReviewDto reviewDto);
    void deleteReview(int reviewId, int pokemonId);
}
