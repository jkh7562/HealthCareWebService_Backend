package com.example.iot_project_backserver.Repository.Data.data;

import com.example.iot_project_backserver.Entity.Data.data.ECG;
import com.example.iot_project_backserver.Entity.Data.data.SPO2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Spo2Repository extends JpaRepository<SPO2,Long>, HealthDataRepository<SPO2> {
    //boolean existsByUserid(String userid);
   // List<SPO2> findByUserid(String userid);
}
