package com.zhfvkq.dyshop.config.repository;

import com.zhfvkq.dyshop.config.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}

