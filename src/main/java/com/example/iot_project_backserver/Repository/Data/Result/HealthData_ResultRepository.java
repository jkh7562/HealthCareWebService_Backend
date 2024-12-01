package com.example.iot_project_backserver.Repository.Data.Result;

import java.util.Date;
import java.util.Optional;

public interface HealthData_ResultRepository <T>{
    Optional<T> findByUseridAndDate(String userid, Date dates);
}
