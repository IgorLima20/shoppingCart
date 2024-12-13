package com.project.shoppingCart.controllers;

import com.project.shoppingCart.models.Product;
import com.project.shoppingCart.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = this.productService.findAll();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product product = this.productService.findById(id);
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody @Valid Product product) {
        Product newProduct = this.productService.save(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody @Valid Product product) {
        product.setId(id);
        Product updateProduct = this.productService.update(product);
        return new ResponseEntity<Product>(updateProduct, HttpStatus.OK);
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        this.productService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
