package com.shoptech.admin.brand;

import com.shoptech.admin.category.CategoryService;
import com.shoptech.admin.upload.FileUploadUtil;
import com.shoptech.admin.user.UserPdfExporter;
import com.shoptech.admin.user.UserService;
import com.shoptech.entity.Brand;
import com.shoptech.entity.Category;
import com.shoptech.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
    public String listFirstPage(Model model) {
        return listByPage(1, model,"name", "asc", null);
    }
    @GetMapping("/brands/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField, @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword){
        Page<Brand> page = service.listByPage(pageNum, sortField, sortDir, keyword);
        List<Brand> listBrands = page.getContent();

        long startCount = (pageNum - 1) * service.BRANDS_PER_PAGE + 1;
        long endCount = startCount + service.BRANDS_PER_PAGE - 1;
        if(endCount > page.getTotalElements())
        {
            endCount = page.getTotalElements();
        }

        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("keyword", keyword);
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
    @GetMapping("/brands/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {

        List<Brand> listBrands = service.listAll();
        BrandPdfExporter exporter = new BrandPdfExporter();
        exporter.export(listBrands, response);
    }
}
