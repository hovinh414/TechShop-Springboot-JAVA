package com.shoptech.admin.order;

import com.shoptech.entity.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderRepository  extends PagingAndSortingRepository<Order, Integer> {
}
