package com.shoptech.site.shipping;

import com.shoptech.entity.Country;
import com.shoptech.entity.ShippingRate;
import org.springframework.data.repository.CrudRepository;

public interface ShippingRateRepository extends CrudRepository<ShippingRate, Integer> {
    public ShippingRate findByCountryAndState(Country country, String state);
}
