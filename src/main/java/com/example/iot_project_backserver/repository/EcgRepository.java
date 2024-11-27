package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.ECG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EcgRepository extends JpaRepository<ECG, Long>, HealthDataRepository<ECG>{
    boolean existsByUserid(String userid);
}


