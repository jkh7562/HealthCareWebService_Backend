package com.example.iot_project_backserver.Repository.Data.data;

import com.example.iot_project_backserver.Entity.Data.data.Airflow;
import com.example.iot_project_backserver.Entity.Data.data.ECG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Airflow 엔티티를 위한 리포지토리
@Repository
public interface AirflowRepository extends JpaRepository<Airflow, Long>, HealthDataRepository<Airflow> {
    // Airflow 관련 쿼리 메서드 추가 가능
    boolean existsByUserid(String userid);
    List<Airflow> findByUserid(String userid);
}
