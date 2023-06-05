package com.shoptech.admin.review;

import java.util.NoSuchElementException;

import com.shoptech.admin.paging.PagingAndSortingHelper;
import com.shoptech.admin.product.ProductRepository;
import com.shoptech.entity.Review;
import com.shoptech.entity.ShippingRate;
import com.shoptech.exception.ReviewNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ReviewService {
	public static final int REVIEWS_PER_PAGE = 5;

	@Autowired private ReviewRepository reviewRepo;
	@Autowired private ProductRepository productRepo;

	public Page<Review> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
		Sort sort = Sort.by(sortField);

		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, REVIEWS_PER_PAGE, sort);

		if (keyword != null && !keyword.isEmpty()) {
			return reviewRepo.findAll(keyword, pageable);
		}

		return reviewRepo.findAll(pageable);
	}

	public Review get(Integer id) throws ReviewNotFoundException {
		try {
			return reviewRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new ReviewNotFoundException("Không tìm thấy đánh giá ID: " + id);
		}
	}

	public void save(Review reviewInForm) {
		Review reviewInDB = reviewRepo.findById(reviewInForm.getId()).get();
		reviewInDB.setHeadline(reviewInForm.getHeadline());
		reviewInDB.setComment(reviewInForm.getComment());

		reviewRepo.save(reviewInDB);
		productRepo.updateReviewCountAndAverageRating(reviewInDB.getProduct().getId());
	}

	public void delete(Integer id) throws ReviewNotFoundException {
		if (!reviewRepo.existsById(id)) {
			throw new ReviewNotFoundException("Không tìm thấy đánh giá ID: " + id);
		}

		reviewRepo.deleteById(id);
	}
}
