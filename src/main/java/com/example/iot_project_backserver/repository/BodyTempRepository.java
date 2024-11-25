package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.BodyTemp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

// BodyTemp 엔티티를 위한 리포지토리
public interface BodyTempRepository extends JpaRepository<BodyTemp, Long>,HealthDataRepository<BodyTemp> {
    // BodyTemp 관련 쿼리 메서드 추가 가능
    boolean existsByUserId(String userId);


    //List<BodyTemp> findByUserIdAndCreatedAt(String userId, LocalDate createdAt);

}
