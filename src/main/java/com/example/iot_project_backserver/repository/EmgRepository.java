package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.EMG;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmgRepository extends JpaRepository<EMG, Long>, HealthDataRepository<EMG>{
    boolean existsByUserid(String userid);
}
