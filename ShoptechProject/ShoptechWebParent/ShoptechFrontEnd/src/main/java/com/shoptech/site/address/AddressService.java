package com.shoptech.site.address;

import com.shoptech.entity.Address;
import com.shoptech.entity.Customer;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AddressService {
    @Autowired private AddressRopository repo;

    public List<Address> listAddressBook(Customer customer){
        return repo.findByCustomer(customer);
    }
    public void save(Address address){
        repo.save(address);
    }
    public Address get(Integer addressId, Integer customerId){
        return repo.findByIdAndCustomer(addressId, customerId);
    }
    public void delete(Integer adressId, Integer customerId){
        repo.deleteByIdAndCustomer(adressId,customerId);
    }
}
