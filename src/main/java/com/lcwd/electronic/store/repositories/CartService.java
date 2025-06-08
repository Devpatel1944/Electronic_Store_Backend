package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {

    //Add Item To Cart
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //Remove Item from Cart
    void removeItemFromCart(String userId,int cartItem);

    //clear Cart
    void clearCart(String userId);

    //Get Cart BY user
    CartDto getCartByUser(String userId);
}
