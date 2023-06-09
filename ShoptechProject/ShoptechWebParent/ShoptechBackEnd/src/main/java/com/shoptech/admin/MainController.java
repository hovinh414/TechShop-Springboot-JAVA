package com.shoptech.admin;


import com.shoptech.admin.product.ProductService;

import com.shoptech.entity.Product;

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
    @GetMapping("/home")
    public  String viewHomePage(Model model){
        Page<Product> pagePro = productService.listByPage(1,"createdTime", "asc",null, null);
        List<Product> listProducts = pagePro.getContent();
        model.addAttribute("listProducts", listProducts);
        return "layouts/index";
    }
    @GetMapping("/login")
    public String viewLoginPage() {
        return "/login";
    }

}
