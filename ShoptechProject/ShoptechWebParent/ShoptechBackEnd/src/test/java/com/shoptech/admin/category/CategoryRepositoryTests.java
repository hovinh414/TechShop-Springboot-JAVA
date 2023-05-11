package com.shoptech.admin.category;

import com.shoptech.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository repo;
    @Test
    public void testCreateRootCategory(){
        Category computers = new Category("Computers");
        Category categoríes = new Category("Electronics");
        repo.saveAll(List.of(computers, categoríes));
    }
    @Test
    public void testCreateSubCategory(){
        Category parent = new Category(1);
        Category desktops = new Category("Desktop", parent);
        Category laptops = new Category("Laptops", parent);
        Category components = new Category("Computer Components", parent);
        repo.saveAll(List.of(desktops, laptops, components));

    }
    @Test
    public void testCreateSubCategory2(){
        Category parent = new Category(2);
        Category cameras = new Category("Cameras", parent);
        Category smartphones = new Category("Smartphones", parent);
        repo.saveAll(List.of(cameras, smartphones));

    }
    @Test
    public void testCreateSubCategory3(){
        Category parent = new Category(5);
        Category memory = new Category("Memory", parent);

        Category savedCategory = repo.save(memory);
        assertThat(savedCategory.getId()).isGreaterThan(0);

    }
    @Test
    public void testCreateSubCategory4(){
        Category parent = new Category(4);
        Category gaminglaptops = new Category("Gaming Laptops", parent);

        Category savedCategory = repo.save(gaminglaptops);
        assertThat(savedCategory.getId()).isGreaterThan(0);

    }
    @Test
    public void testCreateSubCategory5(){
        Category parent = new Category(7);
        Category iphones = new Category("iPhone", parent);

        Category savedCategory = repo.save(iphones);
        assertThat(savedCategory.getId()).isGreaterThan(0);

    }
    @Test
    public void testGetCategory() {
        Category category = repo.findById(2).get();
        System.out.println(category.getName());
        Set<Category> children = category.getChildren();
        for (Category subcategory : children){
            System.out.println(subcategory.getName());
        }
        assertThat(children.size()).isGreaterThan(0);
    }
    @Test
    public void testPrintHierarchicalCategories(){
        Iterable<Category> categories = repo.findAll();
        for (Category category : categories){
            if (category.getParent() == null) {
                System.out.println(category.getName());
                Set<Category> children = category.getChildren();
                for (Category subCategory : children){
                    System.out.println("--" + subCategory.getName());
                    printChildren(subCategory, 1);
                }
            }
        }
    }
    private void printChildren(Category parent, int sublevel) {
        int newSubLevel = sublevel + 1;
        Set<Category> children = parent.getChildren();
        for (Category subCategory : children){
            for (int i = 0; i < newSubLevel; i++) {
                System.out.print("--");
            }
            System.out.println(subCategory.getName());
            printChildren(subCategory, newSubLevel);
        }
    }
}
