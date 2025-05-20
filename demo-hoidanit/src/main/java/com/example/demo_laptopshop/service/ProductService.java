package com.example.demo_laptopshop.service;


import com.example.demo_laptopshop.domain.Product;
import com.example.demo_laptopshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product getProductById(long id){
        return this.productRepository.getById(id);
    }

    public void deleteProductById(long id) {
        this.productRepository.deleteById(id);
    }


}
