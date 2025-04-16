package com.example.pokemonapp.api.service.impl;

import com.example.pokemonapp.api.dto.review.ReviewDto;
import com.example.pokemonapp.api.dto.review.ReviewResponse;
import com.example.pokemonapp.api.exceptions.PokemonNotFoundException;
import com.example.pokemonapp.api.exceptions.ReviewNotFoundException;
import com.example.pokemonapp.api.models.Pokemon;
import com.example.pokemonapp.api.models.Review;
import com.example.pokemonapp.api.repository.PokemonRepository;
import com.example.pokemonapp.api.repository.ReviewRepository;
import com.example.pokemonapp.api.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository){
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review newReview = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

        newReview.setPokemon(pokemon);
        Review savedReview = reviewRepository.save(newReview);

        return mapToDto(savedReview);
    }

    @Override
    public ReviewDto findReviewDetail(int reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        return mapToDto(review);
    }

    @Override
    public List<ReviewDto> getReviewsByPokemonId(int pokemonId) {
        if(!pokemonRepository.existsById(pokemonId)){
            throw new PokemonNotFoundException("Pokemon not found");
        }
        List<Review> reviewList = reviewRepository.findByPokemonId(pokemonId);
        return reviewList.stream().map((r) -> mapToDto(r)).collect(Collectors.toList());
    }

    @Override
    public ReviewResponse getAllReviews(int pageNo, int pageSize) {
        Pageable page = PageRequest.of(pageNo, pageSize);
        Page<Review> reviews = reviewRepository.findAll(page);

        List<Review> reviewList = reviews.getContent();
        List<ReviewDto> content = reviewList.stream().map((r) -> mapToDto(r)).collect(Collectors.toList());

        ReviewResponse response = new ReviewResponse();
        response.setContent(content);
        response.setPageNo(reviews.getNumber());
        response.setPageSize(reviews.getSize());
        response.setTotalElements(reviews.getTotalElements());
        response.setTotalPages(reviews.getTotalPages());
        response.setLast(reviews.isLast());

        return response;
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        if(pokemon.getId() != review.getPokemon().getId()){ // // should be equals, but we have primitive type
            throw new ReviewNotFoundException("This review does not belong to this pokemon");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int reviewId, int pokemonId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        if(pokemon.getId() != review.getPokemon().getId()){  // should be equals, but we have primitive type
            throw new ReviewNotFoundException("This review does not belong to this pokemon");
        }

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        Review updatedReview = reviewRepository.save(review);

        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));

        if(pokemon.getId() != review.getPokemon().getId()){
            throw new ReviewNotFoundException("This review does not belong to the given pokemon");
        }

        reviewRepository.delete(review);
    }


    public ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    public Review mapToEntity(ReviewDto reviewDto){
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }
}
