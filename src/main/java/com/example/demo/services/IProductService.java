package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import com.example.demo.dto.ProductDto;
import com.example.demo.models.Product;

public interface IProductService {
    List<Product> getAllProducts();
    Product createProduct(ProductDto productDto);
    Product upsertProduct(ProductDto productDto, Long id);
    void deleteProduct(Long id);
    Optional<Product> getProductById(Long id);
}
