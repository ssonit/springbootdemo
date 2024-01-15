package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.ProductDto;
import com.example.demo.models.Product;
import com.example.demo.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);        
    }

    @Override
    public List<Product> getAllProducts() {
        
        return productRepository.findAll();
    }

    @Override
    public Product upsertProduct(ProductDto productDto, Long id) {
        Product updatedProduct = productRepository.findById(id).map(product -> {
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setUrl(productDto.getUrl());
            return productRepository.save(product);
        }).orElseGet(() -> {
             Product product = modelMapper.map(productDto, Product.class);
             return productRepository.save(product);
        });
        return updatedProduct;
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);

        return product;
    }

    


    
}
