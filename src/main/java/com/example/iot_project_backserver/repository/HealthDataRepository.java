package com.example.iot_project_backserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HealthDataRepository<T> {
    List<T> findByUserId(String userId); // 특정 사용자 ID로 데이터 조회
    boolean existsByUserId(String userId); // 특정 사용자 ID 존재 여부 확인
}
