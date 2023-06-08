package com.shoptech.site.shoppingcart;

import com.shoptech.entity.Address;
import com.shoptech.entity.Customer;
import com.shoptech.entity.CartItem;
import com.shoptech.entity.ShippingRate;
import com.shoptech.exception.CustomerNotFoundException;
import com.shoptech.site.Utility;
import com.shoptech.site.address.AddressService;
import com.shoptech.site.customer.CustomerService;
import com.shoptech.site.shipping.ShippingRateService;
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
	@Autowired
	private AddressService addressService;
	@Autowired
	private ShippingRateService shipService;
	@GetMapping("/cart")
	public String viewCart(Model model, HttpServletRequest request){
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> cartItems = cartService.listCartItems(customer);
		float estimatedTotal = 0.0F;
		
		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}

		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;
		boolean usePrimaryAddressAsDefault = false;
		if( defaultAddress != null){
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		}else {
			usePrimaryAddressAsDefault = true;
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}

		model.addAttribute("shippingSupported", shippingRate != null);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);
		
		return "cart/shopping_cart";
	}
	@GetMapping("/cartmodal")
	public String viewCart1(Model model, HttpServletRequest request){
		Customer customer = getAuthenticatedCustomer(request);
		List<CartItem> cartItems = cartService.listCartItems(customer);
		float estimatedTotal = 0.0F;

		for (CartItem item : cartItems) {
			estimatedTotal += item.getSubtotal();
		}

		model.addAttribute("cartItems", cartItems);
		model.addAttribute("estimatedTotal", estimatedTotal);

		return "navigation";
	}
	private Customer getAuthenticatedCustomer(HttpServletRequest request) {
		String email = Utility.getEmailOfAuthenticatedCustomer(request);
		return customerService.getCustomerByEmail(email);
	}
}
