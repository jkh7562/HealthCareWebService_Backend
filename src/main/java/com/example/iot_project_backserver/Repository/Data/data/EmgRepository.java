package com.example.iot_project_backserver.Repository.Data.data;

import com.example.iot_project_backserver.Entity.Data.data.EMG;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmgRepository extends JpaRepository<EMG, Long>, HealthDataRepository<EMG>{
    boolean existsByUserid(String userid);
}
