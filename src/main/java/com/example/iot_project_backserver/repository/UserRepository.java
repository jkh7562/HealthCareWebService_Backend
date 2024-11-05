package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserId(String userId); // 사용자 ID 존재 여부 확인
}
