package com.example.iot_project_backserver.Repository.Data.Result;

import com.example.iot_project_backserver.Entity.Data.Result.GSR_Result;
import com.example.iot_project_backserver.Entity.Data.Result.NIBP_Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NIBP_ResultRepository extends JpaRepository<NIBP_Result, Long> {
    Optional<NIBP_Result> findByUseridAndDate(String userid, Date date);
    @Query("SELECT a FROM NIBP_Result a WHERE a.userid = :userid")
    List<NIBP_Result> findByUseridOrNull(@Param("userid") String userid);
}