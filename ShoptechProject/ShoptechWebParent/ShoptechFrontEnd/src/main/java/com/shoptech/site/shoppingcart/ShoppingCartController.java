package com.shoptech.site.shoppingcart;

import com.shoptech.entity.Address;
import com.shoptech.entity.Customer;
import com.shoptech.entity.shoppingcart.CartItem;
import com.shoptech.exception.CustomerNotFoundException;
import com.shoptech.site.Utility;
import com.shoptech.site.customer.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ShoppingCartController {
	@Autowired
	private ShoppingCartService cartService;
	@Autowired
	private CustomerService customerService;
	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request){
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> cartItems = cartService.listCartItems(customer);
		float estimatedTotal = 0.0F;
		
		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);
		
		return "cart/shopping_cart";
	}
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
}
