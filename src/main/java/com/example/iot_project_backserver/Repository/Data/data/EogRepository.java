package com.example.iot_project_backserver.Repository.Data.data;

import com.example.iot_project_backserver.Entity.Data.data.ECG;
import com.example.iot_project_backserver.Entity.Data.data.EOG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Eog 엔티티를 위한 리포지토리
public interface EogRepository extends JpaRepository<EOG, Long>, HealthDataRepository<EOG> {
    // Eog 관련 쿼리 메서드 추가 가능
    //boolean existsByUserid(String userid);
    //List<EOG> findByUserid(String userid);
}
