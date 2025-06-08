package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.OrderDto;
import com.lcwd.electronic.store.dtos.PageableResponse;
import com.lcwd.electronic.store.dtos.ProductDto;
import com.lcwd.electronic.store.entities.*;
import com.lcwd.electronic.store.exception.BadApiRequest;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.OrderRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
     @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ModelMapper modelmapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto, String userId,String cartId) {

          User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
         Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("cart Id Is Invalid!!"));

         List<CartItem> cartItems =cart.getItems();
         if(cartItems.size() <=0){
             throw new BadApiRequest("Cart is NUll!!");
         }

        Order order =Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliverdDate(orderDto.getDeliverdDate())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
         List<OrderItem> orderItems =cartItems.stream().map(cartItem -> {
             OrderItem orderItem =OrderItem.builder()
                     .quantity(cartItem.getQuantity())
                     .product(cartItem.getProduct())
                     .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                     .order(order)
                     .build();
              orderAmount.set(orderAmount.get() + orderItem.getTotalPrice()) ;
              return orderItem;
         }).collect(Collectors.toList());

         order.setOrderItem(orderItems);
         order.setOrderAmount(orderAmount.get());

         cart.getItems().clear();
         cartRepository.save(cart);
         Order order1 =orderRepository.save(order);
        return modelmapper.map(order1,OrderDto.class);

    }

    @Override
    public void removeOrder(String orderId) {
         Order order = orderRepository.findById(orderId).orElseThrow(()->new ResourceNotFoundException("Order is not found!!"));
         orderRepository.delete(order);
    }

    @Override
    public PageableResponse<OrderDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortBy.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pagable = PageRequest.of(pageNumber,pageSize,sort);
       Page<Order> page = orderRepository.findAll(pagable);
       return Helper.getPageableResponse(page , OrderDto.class);

    }

    @Override
    public List<OrderDto> getOrdeByUser(String userId) {
        User user =userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
       List<Order>  orders = orderRepository.findByUser(user);
       List<OrderDto> orderDtos =orders.stream().map(order->modelmapper.map(order ,OrderDto.class)).collect(Collectors.toList());
       return  orderDtos;
    }


}
