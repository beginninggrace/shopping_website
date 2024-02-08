package com.sini.myselectshop.service;

import com.sini.myselectshop.dto.ProductRequestDto;
import com.sini.myselectshop.dto.ProductResponseDto;
import com.sini.myselectshop.entity.Product;
import com.sini.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productRepository.save(new Product(requestDto)); // Product entity에 만들어져있던 생성자 그대로 request에 받아왔던게 담겨서 저장한 객체 product 선언
        return new ProductResponseDto(product);
    }
}
