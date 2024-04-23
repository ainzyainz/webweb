package utils.converter;

import DTO.ReviewDTO;
import entities.Review;

public class ReviewConverter {
    UserConverter userConverter = new UserConverter();
    public Review apply(ReviewDTO reviewDTO){
        return Review.builder()
                .id(reviewDTO.getId())
                .reviewText(reviewDTO.getReviewText())
                .user(userConverter.apply(reviewDTO.getUserDTO()))
                .build();
    }
    public ReviewDTO applyDTO(Review review){
        return ReviewDTO.builder()
                .id(review.getId())
                .reviewText(review.getReviewText())
                .userDTO(userConverter.applyDTO(review.getUser()))
                .build();
    }
}
