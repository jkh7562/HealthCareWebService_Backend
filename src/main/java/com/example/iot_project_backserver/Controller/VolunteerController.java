package com.example.iot_project_backserver.Controller;

import com.example.iot_project_backserver.Dto.CombinedVolunteerData;
import com.example.iot_project_backserver.Dto.PatientVolunteerData;
import com.example.iot_project_backserver.Entity.Volunteer.desired_volunteer_date;
import com.example.iot_project_backserver.Entity.Volunteer.volunteer_assignment;
import com.example.iot_project_backserver.Service.VolunteerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class VolunteerController {
    private final VolunteerService volunteerService;

    @PostMapping("/callvolunteer") // 환자가 봉사자 요청하기(예약 list)
    public ResponseEntity<Map<String, Object>> callvolunteer(@RequestBody Map<String, String> requestData) {
        String userid = requestData.get("userid");
        String desireddate = requestData.get("desireddate");
        String text = requestData.get("text");

        desired_volunteer_date savedEntity = volunteerService.saveDesiredVolunteerDate(userid, desireddate, text);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", savedEntity);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/allvolunteercall") // 봉사자가 예약list, 출장list 요청
    public ResponseEntity<CombinedVolunteerData> allvolunteercall(@RequestBody Map<String, String> requestData) {
        String userid = requestData.get("userid");

        List<desired_volunteer_date> desiredVolunteerDates = volunteerService.getAllDesiredVolunteerDates();
        List<volunteer_assignment> volunteerAssignments = volunteerService.getVolunteerAssignmentsByVolunteerid(userid);

        CombinedVolunteerData combinedData = new CombinedVolunteerData(desiredVolunteerDates, volunteerAssignments);
        return ResponseEntity.ok(combinedData);
    }

    @PostMapping("/patientvolunteercall") // 사용자가 예약list, 출장list 요청
    public ResponseEntity<PatientVolunteerData> patientvolunteercall(@RequestBody Map<String, String> requestData) {
        String userid = requestData.get("userid");

        List<desired_volunteer_date> desiredVolunteerDates = volunteerService.getDesiredVolunteerDatesByUserid(userid);
        List<volunteer_assignment> volunteerAssignments = volunteerService.getVolunteerAssignmentsByUserid(userid);

        PatientVolunteerData responseData = new PatientVolunteerData(desiredVolunteerDates, volunteerAssignments);
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/volunteerassignment") // 봉사자가 예약list 선택, 출장 list에 추가
    public ResponseEntity<Map<String, Object>> volunteerassignment(@RequestBody Map<String, String> requestData) {
        String volunteerid = requestData.get("volunteerid");
        String userid = requestData.get("userid");
        String assignmentdate = requestData.get("assignmentdate");
        String text = requestData.get("text");

        volunteer_assignment savedAssignment = volunteerService.saveVolunteerAssignment(volunteerid, userid, assignmentdate, text);
        volunteerService.deleteDesiredVolunteerDateByUseridAndDate(userid, assignmentdate);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", savedAssignment);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/volunteertime") // 봉사자의 봉사횟수
    public ResponseEntity<Map<String, String>> volunteertime(@RequestBody Map<String, String> requestData) {
        String volunteerid = requestData.get("volunteerid");

        Optional<Integer> volunteerTime = volunteerService.getVolunteerTimeById(volunteerid);
        Map<String, String> response = new HashMap<>();

        if (volunteerTime.isPresent()) {
            response.put("volunteertime", volunteerTime.get().toString());
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Volunteer not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/volunteercomplete") // 봉사자가 봉사완료
    public ResponseEntity<Map<String, Object>> volunteerComplete(@RequestBody Map<String, String> requestData) {
        String volunteerid = requestData.get("volunteerid");
        String userid = requestData.get("userid");
        String assignmentdate = requestData.get("assignmentdate");

        volunteerService.deleteAssignment(volunteerid, userid, assignmentdate);
        volunteerService.incrementVolunteertime(volunteerid);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Assignment record deleted successfully.");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/assignmentcancel") // 환자나 봉사자가 출장list 취소
    public ResponseEntity<Map<String, Object>> assignmentcancel(@RequestBody Map<String, String> requestData) {
        String volunteerid = requestData.get("volunteerid");
        String userid = requestData.get("userid");
        String assignmentdate = requestData.get("assignmentdate");
        String text = requestData.get("text");

        desired_volunteer_date savedEntity = volunteerService.saveDesiredVolunteerDate(userid, assignmentdate, text);
        volunteerService.deleteAssignment(volunteerid, userid, assignmentdate);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", savedEntity);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/volunteercallmodify") // 환자가 예약list 수정
    public ResponseEntity<Map<String, Object>> volunteercallmodify(@RequestBody Map<String, String> requestData) {
        String userid = requestData.get("userid");
        String desireddate = requestData.get("desireddate");
        String text = requestData.get("text");

        boolean isUpdated = volunteerService.updateDesiredVolunteerDate(userid, desireddate, text);

        Map<String, Object> response = new HashMap<>();
        if (isUpdated) {
            response.put("status", "success");
            response.put("message", "Record updated successfully.");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "failure");
            response.put("message", "Record not found.");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PostMapping("/volunteercalldelete") // 환자가 예약list 삭제
    public ResponseEntity<Map<String, Object>> volunteercalldelete(@RequestBody Map<String, String> requestData) {
        String userid = requestData.get("userid");
        String desireddate = requestData.get("desireddate");

        volunteerService.deleteDesiredVolunteerDateByUseridAndDate(userid, desireddate);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Record deleted successfully.");
        return ResponseEntity.ok(response);
    }
}
