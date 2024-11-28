package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.Volunteer.desired_volunteer_date;
import com.example.iot_project_backserver.Entity.Volunteer.volunteer;
import com.example.iot_project_backserver.Entity.Volunteer.volunteer_assignment;

import java.util.List;
import java.util.Optional;

public interface VolunteerService {
    // DesiredService 관련 메소드
    desired_volunteer_date saveDesiredVolunteerDate(String userid, String desireddate, String text);
    List<desired_volunteer_date> getAllDesiredVolunteerDates();
    List<desired_volunteer_date> getDesiredVolunteerDatesByUserid(String userid);
    void deleteDesiredVolunteerDateByUseridAndDate(String userid, String desireddate);
    boolean updateDesiredVolunteerDate(String userid, String desireddate, String text);

    // VolunteerAssignmentService 관련 메소드
    volunteer_assignment saveVolunteerAssignment(String volunteerid, String userid, String assignmentdate, String text);
    List<volunteer_assignment> getVolunteerAssignmentsByVolunteerid(String volunteerid);
    List<volunteer_assignment> getVolunteerAssignmentsByUserid(String userid);
    void deleteAssignment(String volunteerid, String userid, String assignmentdate);

    // VolunteerService 관련 메소드
    volunteer saveVolunteer(volunteer newVolunteer);
    void incrementVolunteertime(String volunteerid);
    Optional<Integer> getVolunteerTimeById(String volunteerid);
}
