package com.shoptech.admin.category;

import com.shoptech.admin.upload.FileUploadUtil;
import com.shoptech.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
@Controller
public class CategoryController {
    @Autowired
    private CategoryService service;
    @GetMapping("/categories")
    public String listAll(Model model){
        List<Category> listCategories = service.listAll();
        model.addAttribute("listCategories",listCategories);
        return "categories/categories";
    }
    @GetMapping("/createcategories")
    public String newCategory(Model model){
        List<Category> listCategories = service.listCategoriesUsedInForm();
        model.addAttribute("category", new Category());
        model.addAttribute("listCategories", listCategories);
        model.addAttribute("pageTitle", "Create new Category");
        return "categories/create";
    }
    @PostMapping("categories/save")
    public String saveCategory(Category category, @RequestParam("fileImage") MultipartFile multipartFile,
                               RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            category.setImage(fileName);

            Category savedCategory = service.save(category);
            String uploadDir = "./category-images/" + savedCategory.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            service.save(category);
        }
        ra.addFlashAttribute("message", "Thêm danh mục thành công!");
        return "redirect:/categories";
    }
    @GetMapping("/editcategories/{id}")
    public String editCategory(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra){
        try {
            Category category = service.get(id);
            List<Category> listCategories = service.listCategoriesUsedInForm();

            model.addAttribute("category", category);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Sửa danh mục (ID: " + id + ")");

            return ("categories/create");
        } catch (CategoryNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/categories";
        }
    }

}
