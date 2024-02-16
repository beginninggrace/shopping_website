package com.sini.myselectshop.repository;

import com.sini.myselectshop.entity.Product;
import com.sini.myselectshop.entity.ProductFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {
}
