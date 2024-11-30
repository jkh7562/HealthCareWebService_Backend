package com.example.iot_project_backserver.Repository.Data.data;

import com.example.iot_project_backserver.Entity.Data.data.ECG;
import com.example.iot_project_backserver.Entity.Data.data.GSR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GsrRepository extends JpaRepository<GSR, Long>, HealthDataRepository<GSR>{
    //boolean existsByUserid(String userid);
    //List<GSR> findByUserid(String userid);
}
