package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.NIBP_Result;
import com.example.iot_project_backserver.Entity.Data.Result.SPO2_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SPO2_ResultRepository extends JpaRepository<SPO2_Result, Long>, JpaSpecificationExecutor<SPO2_Result> {
    //Optional<SPO2_Result> findByUseridAndDate(String userid, Date date);
    @Query("SELECT a FROM SPO2_Result a WHERE a.userid = :userid")
    List<SPO2_Result> findByUseridOrNull(@Param("userid") String userid);
}