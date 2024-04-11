package utils.converter;

import DTO.ReviewDTO;
import entities.Review;

public class ReviewConverter {
    public Review apply(ReviewDTO reviewDTO){
        return Review.builder()
                .id(reviewDTO.getId())
                .reviewText(reviewDTO.getReviewText())
                .build();
    }
    public ReviewDTO applyDTO(Review review){
        return ReviewDTO.builder()
                .id(review.getId())
                .reviewText(review.getReviewText())
                .build();
    }
}
