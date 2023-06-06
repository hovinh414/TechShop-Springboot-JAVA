package com.shoptech.site.product;

import com.shoptech.entity.Product;

import com.shoptech.entity.Review;
import com.shoptech.exception.ProductNotFoundException;
import com.shoptech.site.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 10;

    public static final int SEARCH_RESULTS_PER_PAGE = 10;
    @Autowired
    private ProductRepository repo;
    @Autowired
    private ReviewRepository reviewRepository;
    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "_";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);
        return repo.listByCategory(categoryId, categoryIdMatch, pageable);
    }
    public Product getProduct(String alias) throws ProductNotFoundException {
        Product product = repo.findByAlias(alias);
        if (product == null){
            throw new ProductNotFoundException("Không thể tìm thấy sản phẩm" +alias);
        }
        return product;
    }

    public Page<Product> search(String keyword, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, SEARCH_RESULTS_PER_PAGE);
        return repo.search(keyword, pageable);
    }

}
