package com.lcwd.electronic.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @OneToOne
    @JoinColumn(name ="productId")
    private Product product;
    private int quantity;
    private int totalPrice;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "cartId")
    private Cart cart;

}
