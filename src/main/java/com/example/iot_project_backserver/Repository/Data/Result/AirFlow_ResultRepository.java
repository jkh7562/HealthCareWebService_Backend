package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.AirFlow_Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface AirFlow_ResultRepository extends JpaRepository<AirFlow_Result, Long> {
    Optional<AirFlow_Result> findByUseridAndDate(String userid, Date dates);
}
