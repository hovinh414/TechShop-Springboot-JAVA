package com.shoptech.site.shipping;

import com.shoptech.entity.Address;
import com.shoptech.entity.Customer;
import com.shoptech.entity.ShippingRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShippingRateService {
    @Autowired private ShippingRateRepository repo;

    public ShippingRate getShippingRateForCustomer(Customer customer){
        String state = customer.getState();
        if (state == null || state.isEmpty()){
            state = customer.getCity();
        }
        return repo.findByCountryAndState(customer.getCountry(), state);
    }
    public ShippingRate getShippingRateForAddress(Address address){
        String state = address.getState();
        if (state == null || state.isEmpty()){
            state = address.getCity();
        }
        return repo.findByCountryAndState(address.getCountry(), state);
    }
}
