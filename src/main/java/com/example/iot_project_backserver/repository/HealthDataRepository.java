package com.example.iot_project_backserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface HealthDataRepository<T> {
    List<T> findByUserid(String userid); // 특정 사용자 ID로 데이터 조회
    Optional<T> findOneByUserid(String userid); // 단일 데이터 사용 엔티티의 ID 데이터 조회
    boolean existsByUserid(String userid); // 특정 사용자 ID 존재 여부 확인
}
