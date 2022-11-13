package com.bezkoder.spring.hibernate.onetomany.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.hibernate.onetomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.onetomany.model.Product;
import com.bezkoder.spring.hibernate.onetomany.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ProductController {

  @Autowired
  ProductRepository productRepository;

  @GetMapping("/product")
  public ResponseEntity<List<Product>> getAllProduct(@RequestParam(required = false) String title) {
    List<Product> product = new ArrayList<Product>();

    if (title == null)
      productRepository.findAll().forEach(product::add);
    else
      productRepository.findByTitleContaining(title).forEach(product::add);

    if (product.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @GetMapping("/product/{id}")
  public ResponseEntity<Product> getTutorialById(@PathVariable("id") long id) {
    Product product =productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Product with id = " + id));

    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @PostMapping("/product")
  public ResponseEntity<Product> createProduct(@RequestBody Product product) {
    Product_product= productRepository.save(new Product(product.getTitle(), product.getDescription(), true));
    return new ResponseEntity<>(_product, HttpStatus.CREATED);
  }

  @PutMapping("/product/{id}")
  public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {
    Tutorial _product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Product with id = " + id));

    _product.setTitle(product.getTitle());
    _product.setDescription(product.getDescription());
    _product.setPublished(product.isPublished());
    
    return new ResponseEntity<>(productRepository.save(_product), HttpStatus.OK);
  }

  @DeleteMapping("/product/{id}")
  public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id) {
    productRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/product")
  public ResponseEntity<HttpStatus> deleteAllProduct() {
    productRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/product/published")
  public ResponseEntity<List<Product>> findByPublished() {
    List<Product> product = productRepository.findByPublished(true);

    if (product.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    return new ResponseEntity<>(product, HttpStatus.OK);
  }
}
