package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.NIBP_Result;
import com.example.iot_project_backserver.Entity.Data.Result.SPO2_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SPO2_ResultRepository extends JpaRepository<SPO2_Result, Long> {
    Optional<SPO2_Result> findByUseridAndDate(String userid, Date date);
    List<SPO2_Result> findByUserid(String userid);
}