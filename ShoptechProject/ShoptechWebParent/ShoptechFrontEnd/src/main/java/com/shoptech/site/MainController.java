package com.shoptech.site;
import com.shoptech.entity.Category;
import com.shoptech.entity.Country;
import com.shoptech.entity.Customer;
import com.shoptech.site.category.CategoryService;
import com.shoptech.site.customer.CustomerService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class MainController {
    @Autowired private CategoryService categoryService;
    @Autowired private CustomerService customerService;
    @GetMapping("/")
    public  String viewHomePage(Model model){
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories",listCategories);
        return "layouts/index";
    }
    @GetMapping("/login")
    public String viewLoginPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Country> listCountries = customerService.listAllCountries();

        model.addAttribute("listCountries", listCountries);
        model.addAttribute("customer", new Customer());
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "/login";

        }

        return "redirect:/";
    }
    @PostMapping("/create_customer")
    public String createCustomer(Customer customer, Model model, HttpServletRequest request) throws MessagingException, UnsupportedEncodingException {
        customerService.registerCustomer(customer);
        /*sendVerificationEmail(request, customer);*/
        model.addAttribute("pageTitle", "Registration Succeeded!");

        return "register/register_success";
    }
}