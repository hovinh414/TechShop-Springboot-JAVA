package com.shoptech.admin;


import com.shoptech.admin.order.OrderService;
import com.shoptech.admin.product.ProductService;

import com.shoptech.admin.shippingrate.ShippingRateService;
import com.shoptech.entity.Product;

import com.shoptech.entity.ShippingRate;
import com.shoptech.entity.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class MainController {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    ShippingRateService shippingRateService;

    @GetMapping("/home")
    public  String viewHomePage(Model model){
        Page<Product> pagePro = productService.listByPage(1,"createdTime", "asc",null, null);
        List<Product> listProducts = pagePro.getContent();
        model.addAttribute("listProducts", listProducts);

        Page<Order> pageOrd = orderService.home(1,"orderTime", "desc",null);
        List<Order> listOrders = pageOrd.getContent();
        model.addAttribute("listOrders", listOrders);


        Page<ShippingRate> page = shippingRateService.home(1, "country", "asc", null);
        List<ShippingRate> shippingRates = page.getContent();
        model.addAttribute("shippingRates", shippingRates);

        return "layouts/index";
    }
    @GetMapping("/login")
    public String viewLoginPage() {
        return "/login";
    }

}
