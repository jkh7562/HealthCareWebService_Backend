package com.example.iot_project_backserver.Repository;

import com.example.iot_project_backserver.Entity.NIBP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NIBPRepository extends JpaRepository<NIBP, Long>, HealthDataRepository<NIBP> {
    boolean existsByUserid(String userid);

}
