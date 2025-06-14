package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.CartItem;
import lombok.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto user;
    private List<CartItem> items = new ArrayList<>();
}
