package com.example.pokemonapp.api.repository;

import com.example.pokemonapp.api.models.Pokemon;
import com.example.pokemonapp.api.models.Review;
import com.example.pokemonapp.api.repository.ReviewRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ReviewRepositoryTests {

    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewRepositoryTests(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Test
    public void ReviewRepository_Save_ReturnsReviewNotNull() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        Review savedReview = reviewRepository.save(review);

        Assertions.assertThat(savedReview).isNotNull();
        Assertions.assertThat(savedReview.getId()).isGreaterThan(0);
    }

    @Test
    public void ReviewRepository_FindAll_ReturnsTwoReviews() {
        Review review = Review.builder().title("title").content("content").stars(5).build();
        Review review2 = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);
        reviewRepository.save(review2);

        List<Review> reviewList = reviewRepository.findAll();

        Assertions.assertThat(reviewList).isNotNull();
        Assertions.assertThat(reviewList.size()).isEqualTo(2);
    }

    @Test
    public void ReviewRepository_FindById_ReturnsReviewNotNull() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        Review foundReview = reviewRepository.findById(review.getId()).get();

        Assertions.assertThat(foundReview).isNotNull();
    }

    @Test
    public void ReviewRepository_UpdateReview_ReturnsReviewNotNull() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        Review newReview = reviewRepository.findById(review.getId()).get();
        newReview.setTitle("new title");
        newReview.setContent("new content");
        newReview.setStars(1);

        Review updatedReview = reviewRepository.save(newReview);

        Assertions.assertThat(updatedReview.getTitle()).isNotNull();
        Assertions.assertThat(updatedReview.getContent()).isNotNull();
        Assertions.assertThat(updatedReview.getStars()).isNotNull();
    }

    @Test
    public void ReviewRepository_DeleteReview_ReturnsReviewIsEmpty() {
        Review review = Review.builder().title("title").content("content").stars(5).build();

        reviewRepository.save(review);

        reviewRepository.delete(review);

        Optional<Review> deletedReview = reviewRepository.findById(review.getId());

        Assertions.assertThat(deletedReview).isEmpty();
    }
}
