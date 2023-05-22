package com.shoptech.admin.product;

import com.shoptech.entity.Brand;
import com.shoptech.entity.Category;
import com.shoptech.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductReposityTests {
    @Autowired
    private ProductRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct() {
        Brand brand =entityManager.find(Brand.class, 3); // sửa lại id theo trong database của từng người
        Category category = entityManager.find(Category.class, 7); // cái này cũng vậy vì id cảu mỗi người khác nhau
        Product product = new Product();
        product.setName("Iphone 13 ProMax con chó Quỳnh");
        product.setAlias("iphone_13_promax_con_cho_quynh");
        product.setShortDescription("Một sản phẩm tuyệt vời tới từ Bình Dương");
        product.setFullDescription("Một sản phẩm tuyệt vời tới từ Bình Dương - Full Description");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(25000000);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }
    @Test
    public void testCreateProduct2() {
        Brand brand =entityManager.find(Brand.class, 1);
        Category category = entityManager.find(Category.class, 9);
        Product product = new Product();
        product.setName("Acer nitro 5 con chó Trung");
        product.setAlias("acer nitro 5 con cho Trung");
        product.setShortDescription("Một sản phẩm tuyệt vời tới từ Nha Trang");
        product.setFullDescription("Một sản phẩm tuyệt vời tới từ Nha trang - Full Description");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(22000000);
        product.setCost(20000000);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateProduct3() {
        Brand brand =entityManager.find(Brand.class, 3);
        Category category = entityManager.find(Category.class, 7);
        Product product = new Product();
        product.setName("Winner X con chó Lộc");
        product.setAlias("winner x con cho loc");
        product.setShortDescription("Một con báo tới từ Thủ Đức");
        product.setFullDescription("Một con báo tới từ Thủ Đức - Full Description");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(22000000);
        product.setCost(20000000);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllProduct(){
        Iterable<Product> iterableProducts = repo.findAll();

        iterableProducts.forEach(System.out::println);
    }
    @Test
    public void testGetProduct(){
        Integer id = 2;
        Product product = repo.findById(id).get();
        System.out.println(product);

        assertThat(product).isNotNull();
    }
    @Test
    public void testUpdateProduct(){
        Integer id = 2;
        Product product = repo.findById(id).get();
        product.setPrice(30000000);

        repo.save(product);

        Product updateProduct = entityManager.find(Product.class, id);

        assertThat(updateProduct.getPrice()).isEqualTo(30000000);
    }
}
