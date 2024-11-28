package com.example.iot_project_backserver.Repository.Volunteer;

import com.example.iot_project_backserver.Entity.Volunteer.volunteer_assignment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerAssignmentRepository extends JpaRepository<volunteer_assignment, String> {
    List<volunteer_assignment> findByVolunteerid(String volunteerid);
    List<volunteer_assignment> findByUserid(String userid);
    @Transactional
    @Modifying
    void deleteByVolunteeridAndUseridAndAssignmentdate(String volunteerid, String userid, String assignmentdate);
}
