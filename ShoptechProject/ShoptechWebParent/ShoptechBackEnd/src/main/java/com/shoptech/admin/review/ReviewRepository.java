package com.shoptech.admin.review;


import com.shoptech.admin.paging.SearchRepository;
import com.shoptech.entity.Review;
import com.shoptech.entity.ShippingRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review,Integer>, PagingAndSortingRepository<Review, Integer>,SearchRepository<Review, Integer> {
	
	@Query("SELECT r FROM Review r WHERE r.headline LIKE %?1% OR "
			+ "r.comment LIKE %?1% OR r.product.name LIKE %?1% OR "
			+ "CONCAT(r.customer.firstName, ' ', r.customer.lastName) LIKE %?1%")
	public Page<Review> findAll(String keyword, Pageable pageable);
	
	public List<Review> findAll();
}
