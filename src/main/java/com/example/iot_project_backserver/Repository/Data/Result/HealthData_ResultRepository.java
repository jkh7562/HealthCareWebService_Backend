package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.AirFlow_Result;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HealthData_ResultRepository <T>{
    Optional<T> findByUseridAndDate(String userid, Date dates);
}
