package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.BodyTemp_Result;
import com.example.iot_project_backserver.Entity.Data.Result.EMG_Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EMG_ResultRepository extends JpaRepository<EMG_Result, Long> {
    Optional<EMG_Result> findByUseridAndDate(String userid, Date dates);
    List<EMG_Result> findByUserid(String userid);
}
