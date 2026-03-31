package com.example.ecommerce.order_service.service;

import com.example.ecommerce.order_service.clients.InventoryFeignClient;
import com.example.ecommerce.order_service.dto.OrderRequestDto;
import com.example.ecommerce.order_service.entity.OrderItem;
import com.example.ecommerce.order_service.entity.OrderStatus;
import com.example.ecommerce.order_service.entity.Orders;
import com.example.ecommerce.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {


    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final InventoryFeignClient inventoryFeignClient;

    public List<OrderRequestDto> getAllOrders()
    {
        log.info("Fetching all orders");
        List<Orders> orders=orderRepository.findAll();
        return orders.stream()
                .map((element) -> modelMapper.map(element, OrderRequestDto.class))
                .collect(Collectors.toList());
    }

    public OrderRequestDto getOrderById(Long id)
    {
        log.info("Fetching order with id : {}",id);
        Orders order=orderRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Order not found")
        );

        return modelMapper.map(order,OrderRequestDto.class);
    }

    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        Double totalPrice=inventoryFeignClient.reduceStock(orderRequestDto);

        Orders orders=modelMapper.map(orderRequestDto,Orders.class);
        for(OrderItem orderItem:orders.getItems())
        {
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);

        Orders savedOrder=orderRepository.save(orders);

        return modelMapper.map(savedOrder,OrderRequestDto.class);

    }
}
