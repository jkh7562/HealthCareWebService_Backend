package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.AirFlow_Result;
import com.example.iot_project_backserver.Entity.Data.Result.BodyTemp_Result;
import com.example.iot_project_backserver.Entity.Data.Result.ECG_Result;
import com.example.iot_project_backserver.Entity.Data.Result.EMG_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EMG_ResultRepository extends JpaRepository<EMG_Result, Long>,HealthData_ResultRepository<EMG_Result>  {
    //Optional<EMG_Result> findByUseridAndDate(String userid, Date dates);
    @Query("SELECT a FROM EMG_Result a WHERE a.userid = :userid")
    List<EMG_Result> findByUseridOrNull(@Param("userid") String userid);
}
