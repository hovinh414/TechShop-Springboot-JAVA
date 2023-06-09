package com.shoptech.admin.brand;

import com.shoptech.entity.Brand;
import com.shoptech.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTests {
    @Autowired
    private BrandRepository repo;
    @Test
    public void testCreateBrand1(){
        Category laptops = new Category(9);
        Brand acer = new Brand("Acer");
        acer.getCategories().add(laptops);
        Brand saveBrand = repo.save(acer);
        assertThat(saveBrand).isNotNull();
        assertThat(saveBrand.getId()).isGreaterThan(0);

    }
    @Test
    public void testCreateBrand2(){
        Brand samsung = new Brand("Samsung");
        samsung.getCategories().add(new Category(12));
        samsung.getCategories().add(new Category(7));

        Brand saveBrand = repo.save(samsung);
        assertThat(saveBrand).isNotNull();
        assertThat(saveBrand.getId()).isGreaterThan(0);

    }
    @Test
    public void testCreateBrand3(){
        Category iphone = new Category(12);
        Category smartphone = new Category(16);

        Brand apple = new Brand("Apple");
        apple.getCategories().add(iphone);
        apple.getCategories().add(smartphone);

        Brand saveBrand = repo.save(apple);
        assertThat(saveBrand).isNotNull();
        assertThat(saveBrand.getId()).isGreaterThan(0);

    }
    @Test
    public void testFindAll(){
        Iterable<Brand> brands = repo.findAll();
        brands.forEach(System.out::println);

        assertThat(brands).isNotEmpty();
    }
    @Test
    public void testGetById(){
        Brand brand = repo.findById(1).get();
        assertThat(brand.getName()).isEqualTo("Acer");

    }

    @Test
    public void testUpdatename(){
        String newName = "Samsung Electronic";
        Brand samsung = repo.findById(2).get();
        samsung.setName(newName);

        Brand saveBrand = repo.save(samsung);
        assertThat(saveBrand.getName()).isEqualTo(newName);
    }

    @Test
    public void testDelete(){
        Integer id = 2;
        repo.deleteById(id);

        Optional<Brand> result = repo.findById(id);
        assertThat(result.isEmpty());
    }
}
