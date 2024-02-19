package com.sini.myselectshop.repository;

import com.sini.myselectshop.entity.ApiUseTime;
import com.sini.myselectshop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}
