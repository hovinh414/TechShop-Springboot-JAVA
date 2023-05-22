package com.shoptech.site;
import com.shoptech.entity.Category;
import com.shoptech.site.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired private CategoryService categoryService;
    @GetMapping("/")
    public  String viewHomePage(Model model){
        List<Category> listCategories = categoryService.listAll();
        model.addAttribute("listCategories",listCategories);
        return "layouts/index";
    }
}