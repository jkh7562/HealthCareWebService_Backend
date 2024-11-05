package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.Eog;
import org.springframework.data.jpa.repository.JpaRepository;

// Eog 엔티티를 위한 리포지토리
public interface EogRepository extends JpaRepository<Eog, Long>, HealthDataRepository<Eog> {
    // Eog 관련 쿼리 메서드 추가 가능
}
