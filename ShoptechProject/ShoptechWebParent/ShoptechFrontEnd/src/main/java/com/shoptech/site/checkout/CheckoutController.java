package com.shoptech.site.checkout;

import com.shoptech.entity.Address;
import com.shoptech.entity.CartItem;
import com.shoptech.entity.Customer;
import com.shoptech.entity.ShippingRate;
import com.shoptech.entity.order.Order;
import com.shoptech.entity.order.PaymentMethod;
import com.shoptech.site.Utility;
import com.shoptech.site.address.AddressService;
import com.shoptech.site.checkout.paypal.PayPalApiException;
import com.shoptech.site.checkout.paypal.PayPalService;
import com.shoptech.site.customer.CustomerService;
import com.shoptech.site.order.OrderService;
import com.shoptech.site.setting.PaymentSettingBag;
import com.shoptech.site.setting.SettingService;
import com.shoptech.site.shipping.ShippingRateService;
import com.shoptech.site.shoppingcart.ShoppingCartService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class CheckoutController {
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ShoppingCartService cartService;
    @Autowired
    private ShippingRateService shipService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SettingService settingService;
    @Autowired
    private PayPalService payPalService;
    @GetMapping("/checkout")
    public String showCheckoutPage(Model model, HttpServletRequest request) {
        Customer customer = getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if( defaultAddress != null){
            model.addAttribute("shippingAddress", defaultAddress.toString());
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        }else {
            model.addAttribute("shippingAddress", customer.toString());
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }
        if (shippingRate == null) {
            return "redirect:/cart";
        }

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);
        String currencyCode = settingService.getCurrencyCode();
        PaymentSettingBag paymentSettings = settingService.getPaymentSettings();
        String paypalClientId = paymentSettings.getClientID();

        model.addAttribute("paypalClientId", paypalClientId);
        model.addAttribute("customer", customer);
        model.addAttribute("currencyCode", currencyCode);
        model.addAttribute("checkoutInfo", checkoutInfo);
        model.addAttribute("cartItems", cartItems);

        return "checkout/checkout";
    }

    private Customer getAuthenticatedCustomer(HttpServletRequest request) {
        String email = Utility.getEmailOfAuthenticatedCustomer(request);
        return customerService.getCustomerByEmail(email);
    }
    @PostMapping("/place_order")
    public String placeOrder(HttpServletRequest request){
        String paymentType = request.getParameter("paymentMethod");
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentType);

        Customer customer =getAuthenticatedCustomer(request);

        Address defaultAddress = addressService.getDefaultAddress(customer);
        ShippingRate shippingRate = null;

        if (defaultAddress != null) {
            shippingRate = shipService.getShippingRateForAddress(defaultAddress);
        } else {
            shippingRate = shipService.getShippingRateForCustomer(customer);
        }

        List<CartItem> cartItems = cartService.listCartItems(customer);
        CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);

        Order createdOrder = orderService.createOrder(customer, defaultAddress, cartItems, paymentMethod, checkoutInfo);
        cartService.deleteByCustomer(customer);
        return "checkout/order_completed";
    }
    @PostMapping("/process_paypal_order")
    public String processPayPalOrder(HttpServletRequest request, Model model)
            throws UnsupportedEncodingException, MessagingException {
        String orderId = request.getParameter("orderId");

        String pageTitle = "Checkout Failure";
        String message = null;

        try {
            if (payPalService.validateOrder(orderId)) {
                return placeOrder(request);
            } else {
                pageTitle = "Checkout Failure";
                message = "ERROR: Transaction could not be completed because order information is invalid";
            }
        } catch (PayPalApiException e) {
            message = "ERROR: Transaction failed due to error: " + e.getMessage();
        }

        model.addAttribute("pageTitle", pageTitle);
        model.addAttribute("title", pageTitle);
        model.addAttribute("message", message);

        return "message";
    }
}