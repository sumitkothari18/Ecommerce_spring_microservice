package com.example.ecommerce.order_service.controller;

import com.example.ecommerce.order_service.dto.OrderRequestDto;
import com.example.ecommerce.order_service.dto.OrderRequestItemDto;
import com.example.ecommerce.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    @GetMapping("/helloOrders")
    public String helloOrders()
    {
        return  "Hello from order Service";
    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders()
    {
        List<OrderRequestDto> orderRequestDtos=orderService.getAllOrders();
        return ResponseEntity.ok(orderRequestDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id)
    {
        OrderRequestDto orderRequestDto=orderService.getOrderById(id);
        return ResponseEntity.ok(orderRequestDto);
    }

    @PostMapping("create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto)
    {
        OrderRequestDto orderRequestDto1=orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);
    }
}
