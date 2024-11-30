package com.example.iot_project_backserver.Repository.Data.data;

import com.example.iot_project_backserver.Entity.Data.data.ECG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EcgRepository extends JpaRepository<ECG, Long>, HealthDataRepository<ECG>{
    //boolean existsByUserid(String userid);
    //List<ECG> findByUserid(String userid);
}


