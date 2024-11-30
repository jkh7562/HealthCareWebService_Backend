package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.BodyTemp_Result;
import com.example.iot_project_backserver.Entity.Data.Result.ECG_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ECG_ResultRepository extends JpaRepository<ECG_Result, Long>, HealthData_ResultRepository<ECG_Result> {
    //Optional<ECG_Result> findByUseridAndDate(String userid, Date dates);
    @Query("SELECT a FROM ECG_Result a WHERE a.userid = :userid")
    List<ECG_Result> findByUseridOrNull(@Param("userid") String userid);
}
