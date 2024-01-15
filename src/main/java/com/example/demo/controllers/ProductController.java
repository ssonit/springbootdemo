package com.example.demo.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ProductDto;
import com.example.demo.models.Product;
import com.example.demo.services.IProductService;
import com.example.demo.utils.ResponseObject;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;
    
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllProducts() {
        List<Product> products = productService.getAllProducts();
       return ResponseEntity.status(HttpStatus.OK).body(
        new ResponseObject("ok", "Get all products successfully", products)
       );
    }


    @PostMapping("")
    public ResponseEntity<ResponseObject> createProduct(@RequestBody ProductDto productDto) {
        Product product = productService.createProduct(productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(
            new ResponseObject("ok", "Product created successfully", product)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> upsertProduct(@RequestBody ProductDto productDto, @PathVariable Long id) {
        Product updatedProduct = productService.upsertProduct(productDto, id);

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("ok", "Product updated successfully", updatedProduct)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        boolean exists = productService.getProductById(id).isPresent();

        if(exists) {
            // productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Product deleted successfully", "")
            );
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ResponseObject("ok", "Cannot find product to delete", "")
        );

       

    }


 }
