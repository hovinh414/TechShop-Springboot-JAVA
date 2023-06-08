package com.shoptech.admin.order;


import com.shoptech.admin.paging.PagingAndSortingHelper;
import com.shoptech.admin.setting.country.CountryRepository;
import com.shoptech.entity.Country;
import com.shoptech.entity.order.Order;
import com.shoptech.entity.order.OrderStatus;
import com.shoptech.entity.order.OrderTrack;
import com.shoptech.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
	private static final int ORDERS_PER_PAGE = 10;
	
	@Autowired private OrderRepository orderRepo;
	@Autowired private CountryRepository countryRepo;
	
	public Page<Order> listByPage(int pageNum, PagingAndSortingHelper helper) {
		String sortField = helper.getSortField();
		String sortDir = helper.getSortDir();
		String keyword = helper.getKeyword();
		System.out.println(keyword);
		
		Sort sort = null;
		if ("destination".equals(sortField)) {
			sort = Sort.by("country").and(Sort.by("state")).and(Sort.by("city"));
		} else if(!sortField.isEmpty()) {
			sort = Sort.by(sortField);
		}else{
			sort = Sort.by("orderTime");
		}
		
		sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable = PageRequest.of(pageNum - 1, ORDERS_PER_PAGE, sort);
		
		if (keyword != null) {
			return orderRepo.findAll(keyword, pageable);
		} else {
			return orderRepo.findAll(pageable);
		}
	}
	
	public Order get(Integer id) throws OrderNotFoundException {
		try {
			return orderRepo.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new OrderNotFoundException("Could not find any orders with ID " + id);
		}
	}
	
	public void delete(Integer id) throws OrderNotFoundException {
		Long count = orderRepo.countById(id);
		if (count == null || count == 0) {
			throw new OrderNotFoundException("Could not find any orders with ID " + id);
		}
		
		orderRepo.deleteById(id);
	}

	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}

	public void save(Order orderInForm) {
		Order orderInDB = orderRepo.findById(orderInForm.getId()).get();
		orderInForm.setOrderTime(orderInDB.getOrderTime());
		orderInForm.setCustomer(orderInDB.getCustomer());
		
		orderRepo.save(orderInForm);
	}	
	
	public void updateStatus(Integer orderId, String status) {
		Order orderInDB = orderRepo.findById(orderId).get();
		OrderStatus statusToUpdate = OrderStatus.valueOf(status);
		
		if (!orderInDB.hasStatus(statusToUpdate)) {
			List<OrderTrack> orderTracks = orderInDB.getOrderTracks();
			
			OrderTrack track = new OrderTrack();
			track.setOrder(orderInDB);
			track.setStatus(statusToUpdate);
			track.setUpdatedTime(new Date());
			track.setNotes(statusToUpdate.defaultDescription());
			
			orderTracks.add(track);
			
			orderInDB.setStatus(statusToUpdate);
			
			orderRepo.save(orderInDB);
		}
		
	}
}
