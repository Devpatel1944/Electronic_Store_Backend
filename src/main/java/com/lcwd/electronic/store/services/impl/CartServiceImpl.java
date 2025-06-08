package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exception.BadApiRequest;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();
       if(quantity<=0){
           throw  new BadApiRequest("Requested quantity is not valid");
       }

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("ProductId is Invalid"));
        // fetch the User
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("UserId is Invalid"));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        //perform Cart Operation
        AtomicReference<Boolean> updated = new AtomicReference<Boolean>(false);
        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);
        //Create Items
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
            cart.setUser(user);
            Cart updatedcart = cartRepository.save(cart);
            return mapper.map(updatedcart, CartDto.class);
        }


        @Override
        public void removeItemFromCart (String userId,int cartItem){
              CartItem cartItem1= cartItemRepository.findById(cartItem).orElseThrow(()->new RuntimeException("CartItem Id is Invalid"));
              cartItemRepository.delete(cartItem1);
        }

        @Override
        public void clearCart (String userId){
               User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("UserId is Invalid"));
               Cart cart =cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Not Found"));
               cart.getItems().clear();
               cartRepository.save(cart);
        }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("UserId is Invalid"));
        Cart cart =cartRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Not Found"));
        return mapper.map(cart,CartDto.class);
    }
}
