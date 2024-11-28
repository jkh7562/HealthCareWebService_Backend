package com.example.iot_project_backserver.Repository;

import com.example.iot_project_backserver.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserid(String userid); // 사용자 ID 존재 여부 확인
}
