package com.example.iot_project_backserver.Repository;

import com.example.iot_project_backserver.Entity.SPO2;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Spo2Repository extends JpaRepository<SPO2,Long>, HealthDataRepository<SPO2> {
    boolean existsByUserid(String userid);
}
