package com.bezkoder.spring.hibernate.onetomany.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.hibernate.onetomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.onetomany.model.Comment;
import com.bezkoder.spring.hibernate.onetomany.repository.CategoryRepository;
import com.bezkoder.spring.hibernate.onetomany.repository.ProductRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CategoryController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @GetMapping("/product/{productId}/category")
  public ResponseEntity<List<Category>> getAllCategoryByTutorialId(@PathVariable(value = "productId") Long productId) {
    if (!productRepository.existsById(productId)) {
      throw new ResourceNotFoundException("Not found Tutorial with id = " + productId);
    }

    List<Category> category = categoryRepository.findByProductId(productId);
    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @GetMapping("/category/{id}")
  public ResponseEntity<Category> getCategorysByProductId(@PathVariable(value = "id") Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Category with id = " + id));

    return new ResponseEntity<>(category, HttpStatus.OK);
  }

  @PostMapping("/product/{productId}/category")
  public ResponseEntity<Category> createCategory(@PathVariable(value = "productId") Long productId,
      @RequestBody Category categoryRequest) {
    Category category = productRepository.findById(productId).map(product -> {
      categoryRequest.setProduct(product);
      return categoryRepository.save( categoryRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Product with id = " + productId));

    return new ResponseEntity<>(category, HttpStatus.CREATED);
  }

  @PutMapping("/category/{id}")
  public ResponseEntity<Comment> updateCategory(@PathVariable("id") long id, @RequestBody Category categoryRequest) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("CategoryId " + id + "not found"));

   category.setContent(categoryRequest.getContent());

    return new ResponseEntity<>(categoryRepository.save(category), HttpStatus.OK);
  }

  @DeleteMapping("/category/{id}")
  public ResponseEntity<HttpStatus> deleteCategory(@PathVariable("id") long id) {
    categoryRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/Product/{ProductId}/category")
  public ResponseEntity<List< Category>> deleteAllCategorysOfProduct(@PathVariable(value = "ProductId") Long ProductId) {
    if (!ProductRepository.existsById(ProductId)) {
      throw new ResourceNotFoundException("Not found Product with id = " + ProductId);
    }

    categoryRepository.deleteByProductId(productId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
