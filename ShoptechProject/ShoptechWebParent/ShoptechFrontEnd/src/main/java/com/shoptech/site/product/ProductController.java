package com.shoptech.site.product;

import com.shoptech.entity.*;
import com.shoptech.exception.CategoryNotFoundException;
import com.shoptech.exception.ProductNotFoundException;
import com.shoptech.site.category.CategoryService;
import com.shoptech.site.customer.CustomerService;
import com.shoptech.site.review.ReviewService;
import com.shoptech.site.security.oauth.CustomerOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/products")
    public String listFirstPage(Model model){
        return listByPage(1,model,"name", "asc",null, null);
    }
    @GetMapping("/products/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword,
                             @Param("categoryId") Integer categoryId){
        Page<Product> page = productService.listByPage(pageNum, sortField, sortDir, keyword, categoryId);
        List<Product> listProducts = page.getContent();
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        long startCount = (pageNum - 1) * productService.PRODUCTS_PER_PAGE + 1;
        long endCount = startCount + productService.PRODUCTS_PER_PAGE - 1;
        if(endCount > page.getTotalElements())
        {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        if (categoryId != null) {
            model.addAttribute("categoryId", categoryId);
        }
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listProducts", listProducts);
        model.addAttribute("listCategories", listCategories);
        return "product/products";
    }
    @GetMapping("/products/detail/{id}")
    public String viewProductDetails(@PathVariable("id") Integer id, Model model,
                                     RedirectAttributes ra) {
        try {
            Product product = productService.get(id);
            model.addAttribute("product", product);

            return "product/product_detail_modal";

        } catch (ProductNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());

            return "redirect:/products";
        }
    }
    @GetMapping("/c/{category_alias}")
    public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
                                        Model model) {
        return viewCategoryByPage(alias, 1, model);
    }



    @GetMapping("/c/{category_alias}/page/{pageNum}")
    public String viewCategoryByPage(@PathVariable("category_alias") String alias,
                                     @PathVariable("pageNum") int pageNum,
                                     Model model) {
        try {
            Category category = categoryService.getCategory(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParents(category);
            Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
            List<Product> listProducts = pageProducts.getContent();
            long starCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
            long endCount = starCount + ProductService.PRODUCTS_PER_PAGE - 1;
            if (endCount > pageProducts.getTotalElements()) {
                endCount = pageProducts.getTotalElements();
            }

            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", pageProducts.getTotalPages());
            model.addAttribute("startCount", starCount);
            model.addAttribute("endCount", endCount);
            model.addAttribute("totalItems", pageProducts.getTotalElements());
            model.addAttribute("pageTitle", category.getName());
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("category", category);
            return "product/product_by_item";
        } catch (CategoryNotFoundException ex) {
            return "error/404";
        }
    }

    @GetMapping("/p/{product_alias}")
    public String viewProductDetail(@PathVariable("product_alias") String alias, Model model) {
        try {
            Product product = productService.getProduct(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());
            Page<Review> listReviews = reviewService.list3MostRecentReviewsByProduct(product);
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("product", product);
            model.addAttribute("listReviews", listReviews);
            model.addAttribute("pageTitle",product.getShortName());
            return "product/product_detail";
        } catch (ProductNotFoundException e) {
            return "error/404";
        }
    }

    @GetMapping("/search")
    public String searchFirstPage(@Param("keyword") String keyword, Model model){
        return searchByPage(keyword, 1, model);
    }
    @GetMapping("/search/page/{pageNum}")
    public String searchByPage(@Param("keyword") String keyword,
                         @PathVariable("pageNum") int pageNum,
                         Model model){
        Page<Product> pageProducts = productService.search(keyword, pageNum);
        List<Product> listResult = pageProducts.getContent();

        long starCount = (pageNum - 1) * ProductService.SEARCH_RESULTS_PER_PAGE + 1;
        long endCount = starCount + ProductService.SEARCH_RESULTS_PER_PAGE - 1;
        if (endCount > pageProducts.getTotalElements()) {
            endCount = pageProducts.getTotalElements();
        }

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("startCount", starCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", pageProducts.getTotalElements());
        model.addAttribute("pageTitle", keyword+ "- Search Result");
        model.addAttribute("keyword",keyword);
        model.addAttribute("listResult",listResult);
        return "product/search_result";
    }
}
