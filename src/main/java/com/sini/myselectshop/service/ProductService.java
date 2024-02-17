package com.sini.myselectshop.service;

import com.sini.myselectshop.dto.ProductMypriceRequestDto;
import com.sini.myselectshop.dto.ProductRequestDto;
import com.sini.myselectshop.dto.ProductResponseDto;
import com.sini.myselectshop.entity.Product;
import com.sini.myselectshop.entity.User;
import com.sini.myselectshop.entity.UserRoleEnum;
import com.sini.myselectshop.naver.dto.ItemDto;
import com.sini.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public static final int MIN_MY_PRICE = 100; //  이런 식으로 상수를 만들어서 제어해보겠음

    public ProductResponseDto createProduct(ProductRequestDto requestDto, User user) {
        Product product = productRepository.save(new Product(requestDto, user)); // Product entity에 만들어져있던 생성자 그대로 request에 받아왔던게 담겨서 저장한 객체 product 선언
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

    @Transactional(readOnly = true)
    public Page<ProductResponseDto> getProducts(User user, int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // 사용자 권한 가져와서 ADMIN 이면 전체 조회, USER 면 본인이 추가한 부분 조회
        UserRoleEnum userRoleEnum = user.getRole();

        Page<Product> productList;

        if (userRoleEnum == UserRoleEnum.USER) {
            // 사용자 권한이 USER 일 경우
            productList = productRepository.findAllByUser(user, pageable);
        } else {
            productList = productRepository.findAll(pageable);
        }

        return productList.map(ProductResponseDto::new);
    }

    @Transactional
    public void updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NullPointerException("해당 상품은 존대하지 않습니다."));
        product.updateByItemDto(itemDto);
    }

}
