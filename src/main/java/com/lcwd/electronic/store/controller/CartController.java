package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.repositories.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    //add items to cart
    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest addItemToCartRequest, @PathVariable String userId){
       CartDto cartDto= cartService.addItemToCart(userId,addItemToCartRequest);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    //remove item to cart
    @DeleteMapping("/{userId}/item/{itemId}")
    public ResponseEntity<ApiResponseMessage> removeItemFromCart(@PathVariable int itemId,@PathVariable String userId){
          cartService.removeItemFromCart(userId,itemId);
         ApiResponseMessage responseMessage= ApiResponseMessage.builder()
                  .message("Item Removed!!")
                  .success(true)
                  .status(HttpStatus.OK)
                  .build();
          return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    //clearCart
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ApiResponseMessage> clearCart(@PathVariable String userId){
        cartService.clearCart(userId);
        ApiResponseMessage responseMessage= ApiResponseMessage.builder()
                .message("Cart Cleared!!")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    //Get Cart
    @GetMapping("/get/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId){
        CartDto cartDto= cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }


}
