package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.User.app_user;
import com.example.iot_project_backserver.Entity.Volunteer.desired_volunteer_date;
import com.example.iot_project_backserver.Entity.Volunteer.volunteer;
import com.example.iot_project_backserver.Entity.Volunteer.volunteer_assignment;
import com.example.iot_project_backserver.Repository.User.UserRepository;
import com.example.iot_project_backserver.Repository.Volunteer.DesiredVolunteerDateRepository;
import com.example.iot_project_backserver.Repository.Volunteer.VolunteerAssignmentRepository;
import com.example.iot_project_backserver.Repository.Volunteer.VolunteerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VolunteerServiceImpl implements VolunteerService {

    private final DesiredVolunteerDateRepository desiredVolunteerDateRepository;
    private final VolunteerAssignmentRepository volunteerAssignmentRepository;
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;

    // DesiredService 관련 구현
    @Override
    public desired_volunteer_date saveDesiredVolunteerDate(String userid, String desireddate, String text) {
        Optional<app_user> appUserOptional = userRepository.findById(userid);
        if (appUserOptional.isPresent()) {
            desired_volunteer_date desiredVolunteerDate = desired_volunteer_date.builder()
                    .userid(userid)
                    .desireddate(desireddate)
                    .text(text)
                    .app_user(appUserOptional.get())
                    .build();
            return desiredVolunteerDateRepository.save(desiredVolunteerDate);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userid);
        }
    }

    @Override
    public List<desired_volunteer_date> getAllDesiredVolunteerDates() {
        return desiredVolunteerDateRepository.findAll();
    }

    @Override
    public List<desired_volunteer_date> getDesiredVolunteerDatesByUserid(String userid) {
        return desiredVolunteerDateRepository.findByUserid(userid);
    }

    @Override
    @Transactional
    public void deleteDesiredVolunteerDateByUseridAndDate(String userid, String desireddate) {
        desiredVolunteerDateRepository.deleteByUseridAndDesireddate(userid, desireddate);
    }

    @Override
    @Transactional
    public boolean updateDesiredVolunteerDate(String userid, String desireddate, String text) {
        Optional<desired_volunteer_date> optionalRecord = desiredVolunteerDateRepository.findByUseridAndDesireddate(userid, desireddate);
        if (optionalRecord.isPresent()) {
            desired_volunteer_date record = optionalRecord.get();
            record.setText(text);
            desiredVolunteerDateRepository.save(record);
            return true;
        } else {
            return false;
        }
    }

    // VolunteerAssignmentService 관련 구현
    @Override
    public volunteer_assignment saveVolunteerAssignment(String volunteerid, String userid, String assignmentdate, String text) {
        volunteer_assignment volunteerAssignment = volunteer_assignment.builder()
                .volunteerid(volunteerid)
                .userid(userid)
                .assignmentdate(assignmentdate)
                .text(text)
                .build();
        return volunteerAssignmentRepository.save(volunteerAssignment);
    }

    @Override
    public List<volunteer_assignment> getVolunteerAssignmentsByVolunteerid(String volunteerid) {
        return volunteerAssignmentRepository.findByVolunteerid(volunteerid);
    }

    @Override
    public List<volunteer_assignment> getVolunteerAssignmentsByUserid(String userid) {
        return volunteerAssignmentRepository.findByUserid(userid);
    }

    @Override
    public void deleteAssignment(String volunteerid, String userid, String assignmentdate) {
        volunteerAssignmentRepository.deleteByVolunteeridAndUseridAndAssignmentdate(volunteerid, userid, assignmentdate);
    }

    // VolunteerService 관련 구현
    @Override
    public volunteer saveVolunteer(volunteer newVolunteer) {
        return volunteerRepository.save(newVolunteer);
    }

    @Override
    public void incrementVolunteertime(String volunteerid) {
        volunteerRepository.incrementVolunteertime(volunteerid);
    }

    @Override
    public Optional<Integer> getVolunteerTimeById(String volunteerid) {
        Optional<volunteer> volunteerRecord = volunteerRepository.findById(volunteerid);
        return volunteerRecord.map(volunteer::getVolunteertime);
    }
}
