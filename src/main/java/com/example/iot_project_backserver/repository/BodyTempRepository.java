package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.BodyTemp;

// BodyTemp 엔티티를 위한 리포지토리
public interface BodyTempRepository extends HealthDataRepository<BodyTemp> {
    // BodyTemp 관련 쿼리 메서드 추가 가능
}
