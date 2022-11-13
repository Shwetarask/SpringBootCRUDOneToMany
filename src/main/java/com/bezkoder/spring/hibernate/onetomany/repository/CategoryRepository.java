package com.bezkoder.spring.hibernate.onetomany.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.hibernate.onetomany.model.Comment;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  List<Category> findByProductId(Long postId);
  
  @Transactional
  void deleteByProductId(long productId);
}
