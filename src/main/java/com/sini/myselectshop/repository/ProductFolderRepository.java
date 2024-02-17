package com.sini.myselectshop.repository;

import com.sini.myselectshop.entity.Folder;
import com.sini.myselectshop.entity.Product;
import com.sini.myselectshop.entity.ProductFolder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductFolderRepository extends JpaRepository<ProductFolder, Long> {
    Optional<ProductFolder> findByProductAndFolder(Product product, Folder folder);
}
