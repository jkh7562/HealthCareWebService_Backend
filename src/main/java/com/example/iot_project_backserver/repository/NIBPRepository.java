package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.NIBP;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NIBPRepository extends JpaRepository<NIBP, Long>, HealthDataRepository<NIBP> {
    boolean existsByUserid(String userid);

}
