package com.example.iot_project_backserver.repository;


import com.example.iot_project_backserver.entity.EOG_Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface EOG_ResultRepository extends JpaRepository<EOG_Result, Long> {
    Optional<EOG_Result> findByUseridAndDate(String userid, Date dates);
}
