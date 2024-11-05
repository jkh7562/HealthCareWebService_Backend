package com.example.iot_project_backserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//데이터에 대한 공통적인 메서드 정의를 위한 인터페이스
public interface HealthDataRepository<T> {
    List<T> findByUserId(Long userId); // 특정 사용자 ID에 대한 데이터를 조회하는 메서드
}
