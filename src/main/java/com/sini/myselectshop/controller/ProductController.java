package com.sini.myselectshop.controller;

import com.sini.myselectshop.dto.ProductMypriceRequestDto;
import com.sini.myselectshop.dto.ProductRequestDto;
import com.sini.myselectshop.dto.ProductResponseDto;
import com.sini.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
        return productService.createProduct(requestDto);
    }

    @PutMapping("/products/{id}") // pathvariable 방식으로 id 넘어오니 이런 식으로 uri 작성
    public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) {
        return productService.updateProduct(id, requestDto);
    }
}
