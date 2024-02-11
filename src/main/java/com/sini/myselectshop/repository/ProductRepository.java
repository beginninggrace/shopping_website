package com.sini.myselectshop.repository;

import com.sini.myselectshop.entity.Product;
import com.sini.myselectshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByUser(User user);
}
