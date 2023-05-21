package com.shoptech.admin.brand;

import com.shoptech.admin.category.CategoryService;
import com.shoptech.admin.upload.FileUploadUtil;
import com.shoptech.entity.Brand;
import com.shoptech.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class BrandController {
    @Autowired
    private BrandService service;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/brands")
    public String listAll(Model model) {
        List<Brand> listBrands = service.listAll();
        model.addAttribute("listBrands", listBrands);
        return "brands/brands";
    }

    @GetMapping("/brands/new")
    public String newBrand(Model model) {
        List<Category> listCategories = categoryService.listCategoriesUsedInForm();

        model.addAttribute("listCategories", listCategories);
        model.addAttribute("brand", new Brand());
        model.addAttribute("pageTitle", "Thêm thương hiệu");

        return "brands/brand_form";
    }
    @PostMapping("/brands/save")
    public String saveBrand(@ModelAttribute("brand") Brand brand,
                            @RequestParam("fileImage") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brand.setLogo(fileName);

            Brand savedBrand = service.save(brand);
            String uploadDir = "../brand-logos/" + savedBrand.getId();

            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            service.save(brand);
        }

        ra.addFlashAttribute("message", "Lưu thương hiệu thành công");
        return "redirect:/brands";
    }
    @GetMapping("/brands/edit/{id}")
    public String editBrand(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Brand brand = service.get(id);
            List<Category> listCategories = categoryService.listCategoriesUsedInForm();

            model.addAttribute("brand", brand);
            model.addAttribute("listCategories", listCategories);
            model.addAttribute("pageTitle", "Sửa thương hiệu` (ID: " + id + ")");

            return "brands/brand_form";
        } catch (BrandNotFoundException ex) {
            ra.addFlashAttribute("message", ex.getMessage());
            return "redirect:/brands";
        }
    }

    @GetMapping("/brands/delete/{id}")
    public String deleteBrand(@PathVariable(name = "id") Integer id, Model model,
                              RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            String brandDir = "../brand-logos/" + id;
            FileUploadUtil.cleanDir(brandDir);

            redirectAttributes.addFlashAttribute("message", "Đã xóa thương hiệu ID " + id);
        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }

        return "redirect:/brands";
    }
}
