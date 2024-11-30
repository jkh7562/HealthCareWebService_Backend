package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.AirFlow_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AirFlow_ResultRepository extends JpaRepository<AirFlow_Result, Long>, HealthData_ResultRepository<AirFlow_Result> {
    //Optional<AirFlow_Result> findByUseridAndDate(String userid, Date dates);
    @Query("SELECT a FROM AirFlow_Result a WHERE a.userid = :userid")
    List<AirFlow_Result> findByUseridOrNull(@Param("userid") String userid);
}
