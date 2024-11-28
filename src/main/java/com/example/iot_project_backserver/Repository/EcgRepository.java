package com.example.iot_project_backserver.Repository;

import com.example.iot_project_backserver.Entity.ECG;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcgRepository extends JpaRepository<ECG, Long>, HealthDataRepository<ECG>{
    boolean existsByUserid(String userid);
}


