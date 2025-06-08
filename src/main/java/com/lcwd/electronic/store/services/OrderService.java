package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.entities.User;

import java.util.List;

public interface OrderService {

    //create Order
    OrderDto createOrder(OrderDto orderDto , String userId,String cartId);

    //remove Order
     void removeOrder(String orderId);
    //Get All order
    PageableResponse<OrderDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);

    //get order by users
    List<OrderDto> getOrdeByUser(String userId);
}
