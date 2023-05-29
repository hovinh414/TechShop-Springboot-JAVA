package com.shoptech.site.shoppingcart;

import com.shoptech.entity.CartItem;
import com.shoptech.entity.Customer;
import com.shoptech.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {
    @Autowired
    private CartItemRepository cartRepo;
    public Integer addProduct(Integer productId, Integer quantity, Customer customer){
        Integer updateQuantity = quantity;
        Product product = new Product(productId);

        CartItem cartItem = cartRepo.findByCustomerAndProduct(customer, product);
        if(cartItem != null){
            updateQuantity = cartItem.getQuantity() + quantity;
        }else {
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
        }
        cartItem.setQuantity(updateQuantity);
        cartRepo.save(cartItem);
        return updateQuantity;
    }
}
