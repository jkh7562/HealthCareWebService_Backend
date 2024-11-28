package com.example.iot_project_backserver.Repository;

import com.example.iot_project_backserver.Entity.ECG_Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ECG_ResultRepository extends JpaRepository<ECG_Result, Long> {
    Optional<ECG_Result> findByUseridAndDate(String userid, Date dates);
    List<ECG_Result> findByUserid(String userid);
}
