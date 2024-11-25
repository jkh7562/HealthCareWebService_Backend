package com.example.iot_project_backserver.repository;


import com.example.iot_project_backserver.entity.EOF;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EofRepository extends JpaRepository<EOF, Long>, HealthDataRepository<EOF>{
    boolean existsByUserId(String userId);
}
