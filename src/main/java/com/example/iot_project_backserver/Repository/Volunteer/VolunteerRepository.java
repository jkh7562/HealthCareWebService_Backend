package com.example.iot_project_backserver.Repository.Volunteer;

import com.example.iot_project_backserver.Entity.Volunteer.volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VolunteerRepository extends JpaRepository<volunteer, String> {

    @Transactional
    @Modifying
    @Query("UPDATE volunteer v SET v.volunteertime = v.volunteertime + 1 WHERE v.volunteerid = :volunteerid")
    void incrementVolunteertime(String volunteerid);
}
