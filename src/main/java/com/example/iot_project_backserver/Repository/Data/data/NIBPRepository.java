package com.example.iot_project_backserver.Repository.Data.data;

import com.example.iot_project_backserver.Entity.Data.data.NIBP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NIBPRepository extends JpaRepository<NIBP, Long>, HealthDataRepository<NIBP> {
    boolean existsByUserid(String userid);

}
