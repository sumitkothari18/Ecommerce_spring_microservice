package com.example.ecommerce.inventory_service.controller;

import com.example.ecommerce.inventory_service.clients.OrdersFeignClient;
import com.example.ecommerce.inventory_service.dto.OrderRequestDto;
import com.example.ecommerce.inventory_service.dto.ProductDto;
import com.example.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrders")
    public String fetchFromOrdersService()
    {
//        ServiceInstance orderService=discoveryClient.getInstances("order-service").getFirst();
//        return restClient.get()
//                 .uri(orderService.getUri()+"/orders/core/helloOrders")
//                 .retrieve()
//                 .body(String.class);

        return ordersFeignClient.helloOrders();
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventory()
    {
        List<ProductDto> productDtos=productService.getAllInventory();

        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id)
    {
        ProductDto productDto= productService.getProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("reduce-stock")
    public ResponseEntity<Double> reduceStock(@RequestBody OrderRequestDto orderRequestDto)
    {
        Double totalPrice=productService.reduceStock(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }


}
