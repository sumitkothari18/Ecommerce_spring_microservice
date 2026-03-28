package com.example.ecommerce.inventory_service.service;

import com.example.ecommerce.inventory_service.dto.ProductDto;
import com.example.ecommerce.inventory_service.entity.Product;
import com.example.ecommerce.inventory_service.exception.ResourceNotFoundException;
import com.example.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public List<ProductDto> getAllInventory()
    {
        log.info("Fetching all inventory items");
        List<Product> products=productRepository.findAll();

        return products.stream()
                .map((element) -> modelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(Long id)
    {
        log.info("Fetching product with id : {}",id);
        Product product=productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found with id: "+id)
        );
        return modelMapper.map(product,ProductDto.class);
    }
}
