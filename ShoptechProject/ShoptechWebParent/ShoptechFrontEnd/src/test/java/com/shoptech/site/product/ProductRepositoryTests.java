package com.shoptech.site.product;

import com.shoptech.entity.Category;
import com.shoptech.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@EntityScan("com.shoptech.entity")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {
    @Autowired ProductRepository repo;

    @Test
    public void testFindByAlias(){
        String alias = "acer_nitro_5_con_cho_Trung";
        Product product = repo.findByAlias(alias);
        assertThat(product).isNotNull();
    }

}
