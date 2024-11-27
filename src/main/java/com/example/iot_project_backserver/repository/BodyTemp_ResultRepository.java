package com.example.iot_project_backserver.repository;

import com.example.iot_project_backserver.entity.BodyTemp_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface BodyTemp_ResultRepository extends JpaRepository<BodyTemp_Result, Long> {
    Optional<BodyTemp_Result> findByUseridAndDate(String userid, Date date);
}
