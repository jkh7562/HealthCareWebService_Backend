package com.example.iot_project_backserver.Repository.Data.Result;


import com.example.iot_project_backserver.Entity.Data.Result.AirFlow_Result;
import com.example.iot_project_backserver.Entity.Data.Result.EOG_Result;
import com.example.iot_project_backserver.Entity.Data.Result.GSR_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface GSR_ResultRepository extends JpaRepository<GSR_Result, Long>, HealthData_ResultRepository<GSR_Result> {
    @Query("SELECT a FROM GSR_Result a WHERE a.userid = :userid")
    List<GSR_Result> findByUseridOrNull(@Param("userid") String userid);
}
