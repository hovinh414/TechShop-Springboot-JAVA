package com.shoptech.admin.product;

import com.shoptech.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository  extends CrudRepository<Product,Integer> {
}
