package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.NIBP_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface NIBP_ResultRepository extends JpaRepository<NIBP_Result, Long> {
    Optional<NIBP_Result> findByUseridAndDate(String userid, Date date);
}