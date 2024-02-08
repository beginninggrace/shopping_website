package com.sini.myselectshop.service;

import com.sini.myselectshop.dto.ProductMypriceRequestDto;
import com.sini.myselectshop.dto.ProductRequestDto;
import com.sini.myselectshop.dto.ProductResponseDto;
import com.sini.myselectshop.entity.Product;
import com.sini.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public static final int MIN_MY_PRICE = 100; //  이런 식으로 상수를 만들어서 제어해보겠음

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        Product product = productRepository.save(new Product(requestDto)); // Product entity에 만들어져있던 생성자 그대로 request에 받아왔던게 담겨서 저장한 객체 product 선언
        return new ProductResponseDto(product);
    }

    // 관심상품 희망 최저가 업데이트 로직 - 최저가격 설정하는거(선택한 상품의 최저가가 떴을 때 표시해준대유)
    @Transactional // dirty checking이 되어서 뱐경 감지하고 수정이 완료됨
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + "원 이상으로 설정해 주세요.");
        }

        // id에 해당하는 product가 있는지 확인&수정도 해야하니 가져오기
        Product product = productRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다."));

        product.update(requestDto); // 위에 만들어둔 myprice 전달해도 되는데 requestDto 전달해주겠음

        return new ProductResponseDto(product);
    }
}
