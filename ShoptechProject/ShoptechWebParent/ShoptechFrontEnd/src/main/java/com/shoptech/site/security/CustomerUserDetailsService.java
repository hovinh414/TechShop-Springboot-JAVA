package com.shoptech.site.security;

import com.shoptech.entity.Customer;
import com.shoptech.site.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomerRepository repo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Customer customer = repo.findByEmail(email);
        if (customer == null)
            throw new UsernameNotFoundException("Kong tim thay email" + email);

        return new CustomerUserDetails(customer);
    }
}




