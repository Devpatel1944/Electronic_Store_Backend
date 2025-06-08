package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dtos.*;
import com.lcwd.electronic.store.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
        @Autowired
        private OrderService orderService;

        //create
    @PostMapping("/create/{userId}/{cartId}")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto, @PathVariable String userId ,@PathVariable String cartId){
                OrderDto orderDto1 =orderService.createOrder(orderDto,userId,cartId);
                return new ResponseEntity<>(orderDto1, HttpStatus.CREATED);
    }
    //remove order
    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){
      orderService.removeOrder(orderId);
      ApiResponseMessage msg =ApiResponseMessage.builder().message("order removed!!").status(HttpStatus.OK).success(true).build();
      return  new ResponseEntity<>(msg,HttpStatus.OK);
    }

    //get order of users
    @GetMapping("/getByUserOrder/{userId}")
    public ResponseEntity<List<OrderDto>>  getOrderOfUser(@PathVariable String userId){
        List<OrderDto> orderOfUser = orderService.getOrdeByUser(userId);
        return new ResponseEntity<>(orderOfUser,HttpStatus.OK);
    }

    //Get All Order
    @GetMapping("/getAllOrder/")
    public ResponseEntity<PageableResponse<OrderDto>> gettOrder(

            @RequestParam(name = "pageNumber" ,defaultValue ="0",required = false) int pageNumber,
            @RequestParam(name = "pageSize" ,defaultValue = "10" ,required = false) int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "title" ,required = false) String sortBy,
            @RequestParam(name ="sortDir",defaultValue = "asc",required = false) String sortDir){
        PageableResponse<OrderDto> pageableResponse =orderService.getAll(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
}
