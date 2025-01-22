package com.project.shoppingCart.services;

import com.project.shoppingCart.models.Product;
import com.project.shoppingCart.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> product = this.productRepository.findById(id);
        return product.orElseThrow(() -> new EntityNotFoundException("Produto não encontrado!"));
    }

    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    public Product update(Product product) {
        return this.productRepository.save(product);
    }

    public void delete(Long id) {
        this.findById(id);
        this.productRepository.deleteById(id);
    }
}
