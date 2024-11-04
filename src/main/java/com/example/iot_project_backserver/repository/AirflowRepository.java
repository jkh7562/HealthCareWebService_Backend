package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.Airflow;

// Airflow 엔티티를 위한 리포지토리
public interface AirflowRepository extends HealthDataRepository<Airflow> {
    // Airflow 관련 쿼리 메서드 추가 가능
}
