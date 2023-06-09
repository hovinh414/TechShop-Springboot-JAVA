package com.shoptech.site.review;

import com.shoptech.entity.Customer;
import com.shoptech.entity.Product;
import com.shoptech.entity.Review;
import com.shoptech.exception.ReviewNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReviewService {
    public static final int REVIEWS_PER_PAGE = 5;

    @Autowired
    private ReviewRepository reviewRepo;
    public Page<Review> listByCustomerByPage(Customer customer, String keyword, int pageNum,
                                             String sortField, String sortDir) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE, sort);

        if (keyword != null) {
            return reviewRepo.findByCustomer(customer.getId(), keyword, pageable);
        }

        return reviewRepo.findByCustomer(customer.getId(), pageable);
    }
    public Review getByCustomerAndId(Customer customer, Integer reviewId) throws ReviewNotFoundException {
        Review review = reviewRepo.findByCustomerAndId(customer.getId(), reviewId);
        if (review == null)
            throw new ReviewNotFoundException("Customer doesn not have any reviews with ID " + reviewId);

        return review;
    }
    public Page<Review> list3MostVotedReviewsByProduct(Product product) {
        Sort sort = Sort.by("reviewTime").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);

        return reviewRepo.findByProduct(product, pageable);
    }
    public void saveReview(Review review) {
        review.setReviewTime(new Date());
        reviewRepo.save(review);
    }
}
