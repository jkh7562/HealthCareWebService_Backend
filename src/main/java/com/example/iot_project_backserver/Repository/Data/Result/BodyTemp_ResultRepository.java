package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.AirFlow_Result;
import com.example.iot_project_backserver.Entity.Data.Result.BodyTemp_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BodyTemp_ResultRepository extends JpaRepository<BodyTemp_Result, Long> {
    Optional<BodyTemp_Result> findByUseridAndDate(String userid, Date date);
    List<BodyTemp_Result> findByUserid(String userid);
}
