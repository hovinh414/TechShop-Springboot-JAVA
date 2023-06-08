package com.shoptech.site.shipping;

import com.shoptech.entity.Country;
import com.shoptech.entity.ShippingRate;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ShippingRateRepositoryTest {

    @Autowired private ShippingRateRepository repo;

    @Test
    public void testFindByCountryAndState(){
        Country usa = new Country(234);
        String state = "New York";
        ShippingRate shippingRate = repo.findByCountryAndState(usa, state);

        assertThat(shippingRate).isNotNull();
        System.out.println(shippingRate);
    }
}
