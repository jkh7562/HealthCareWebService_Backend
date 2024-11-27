package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.ECG_Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ECG_ResultRepository extends JpaRepository<ECG_Result, Long> {
}
