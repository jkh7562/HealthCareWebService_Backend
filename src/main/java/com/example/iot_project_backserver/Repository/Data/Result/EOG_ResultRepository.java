package com.example.iot_project_backserver.Repository.Data.Result;


import com.example.iot_project_backserver.Entity.Data.Result.AirFlow_Result;
import com.example.iot_project_backserver.Entity.Data.Result.EMG_Result;
import com.example.iot_project_backserver.Entity.Data.Result.EOG_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EOG_ResultRepository extends JpaRepository<EOG_Result, Long>,HealthData_ResultRepository<EOG_Result>  {
    //Optional<EOG_Result> findByUseridAndDate(String userid, Date dates);
    @Query("SELECT a FROM EOG_Result a WHERE a.userid = :userid")
    List<EOG_Result> findByUseridOrNull(@Param("userid") String userid);
}
