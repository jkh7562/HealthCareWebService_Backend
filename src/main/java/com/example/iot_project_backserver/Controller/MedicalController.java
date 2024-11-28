package com.example.iot_project_backserver.Controller;

import com.example.iot_project_backserver.Entity.Medical.patient_assignment;
import com.example.iot_project_backserver.Entity.User.required_measurements;
import com.example.iot_project_backserver.Service.MedicalService;
import com.example.iot_project_backserver.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MedicalController {

    private final UserService userService;
    private final MedicalService medicalService;

    @PostMapping("/searchpatient") // 의료진이 환자를 추가하기 위해 검색
    public ResponseEntity<Map<String, Object>> searchpatient(@RequestBody Map<String, String> requestData) {
        String searchdata = requestData.get("searchdata");
        Map<String, Object> response = new HashMap<>();

        if (searchdata.contains("@")) {
            // searchdata를 userid로 간주하고 조회
            Optional<Map<String, String>> userInfo = userService.getUserInfoByUserid(searchdata);
            if (userInfo.isPresent()) {
                Map<String, String> userData = userInfo.get(); // Optional 안의 Map을 꺼냄
                response.put("status", "success");
                response.put("userid", userData.get("userid"));
                response.put("name", userData.get("name"));
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "DataEmpty");
                response.put("userid", null);
                response.put("name", null);
                return ResponseEntity.ok(response);
            }
        } else {
            List<Map<String, String>> userInfoList = userService.getUserInfoByName(searchdata);
            if (!userInfoList.isEmpty()) {
                response.put("status", "success");
                // 여러 개의 유저 정보를 리스트로 담아 response에 추가
                List<Map<String, String>> userData = new ArrayList<>();
                for (Map<String, String> userInfo : userInfoList) {
                    Map<String, String> userMap = new HashMap<>();
                    userMap.put("userid", userInfo.get("userid"));
                    userMap.put("name", userInfo.get("name"));
                    userData.add(userMap);
                }
                response.put("data", userData);
            } else {
                response.put("status", "DataEmpty");
                response.put("data", null);
            }
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/assignmentpatient") // 의료진이 담당 환자 추가
    public ResponseEntity<Map<String, Object>> assignmentpatient(@RequestBody Map<String, String> requestData) {
        String medicalid = requestData.get("medicalid");
        String userid = requestData.get("userid");
        Map<String, Object> response = new HashMap<>();

        boolean exists = medicalService.checkAssignmentExists(medicalid, userid);
        if (exists) {
            response.put("status", "duplication");
        } else {
            medicalService.saveAssignment(medicalid, userid);
            response.put("status", "success");
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/loadpatient") // 의료진이 담당 환자 리스트 받기
    public ResponseEntity<Map<String, Object>> loadpatient(@RequestBody Map<String, String> requestData) {
        String medicalid = requestData.get("medicalid");
        List<patient_assignment> assignments = medicalService.findAssignmentsByMedicalid(medicalid);

        Map<String, Object> response = new HashMap<>();
        if (!assignments.isEmpty()) {
            response.put("status", "success");

            response.put("data", assignments.stream().map(assignment -> {
                Map<String, Object> assignmentData = new HashMap<>();
                assignmentData.put("medicalid", assignment.getMedicalid());
                assignmentData.put("userid", assignment.getUserid());

                if (assignment.getApp_user() != null) {
                    Map<String, Object> userData = new HashMap<>();
                    userData.put("userid", assignment.getApp_user().getUserid());
                    userData.put("name", assignment.getApp_user().getName());
                    userData.put("birth", assignment.getApp_user().getBirth());
                    assignmentData.put("app_user", userData);
                } else {
                    assignmentData.put("app_user", null);
                }

                return assignmentData;
            }).collect(Collectors.toList()));
        } else {
            response.put("status", "DataEmpty");
            response.put("data", null);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/deletepatient") // 의료진이 담당 환자 리스트 삭제
    public ResponseEntity<Map<String, Object>> deletepatient(@RequestBody Map<String, String> requestData) {
        String medicalid = requestData.get("medicalid");
        String userid = requestData.get("userid");
        Map<String, Object> response = new HashMap<>();
        boolean isDeleted = medicalService.deletePatientAssignment(medicalid, userid);
        response.put("success", isDeleted);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/loadmeasure") // 담당 환자의 필요 측정 요소 리스트를 불러오기
    public ResponseEntity<Map<String, Object>> loadmeasure(@RequestBody Map<String, String> requestData) {
        String userid = requestData.get("userid");
        required_measurements result = medicalService.checkAndInsert(userid);

        Map<String, Object> response = new HashMap<>();
        response.put("userid", result.getUserid());
        response.put("airflow", result.getAirflow());
        response.put("bodytemp", result.getBodytemp());
        response.put("nibp", result.getNibp());
        response.put("spo2", result.getSpo2());
        response.put("ecg", result.getEcg());
        response.put("emg", result.getEmg());
        response.put("gsr", result.getGsr());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/modifymeasure") // 담당 환자의 필요 측정 요소 리스트를 수정하기
    public ResponseEntity<Map<String, Object>> modifymeasure(@RequestBody Map<String, String> requestData) {
        String userid = requestData.get("userid");
        String airflow = requestData.get("airflow");
        String bodytemp = requestData.get("bodytemp");
        String nibp = requestData.get("nibp");
        String spo2 = requestData.get("spo2");
        String ecg = requestData.get("ecg");
        String emg = requestData.get("emg");
        String gsr = requestData.get("gsr");

        required_measurements updatedMeasurements = medicalService.updateMeasurements(userid, airflow, bodytemp, nibp, spo2, ecg, emg, gsr);

        Map<String, Object> response = new HashMap<>();
        if (updatedMeasurements != null) {
            response.put("status", "success");
            response.put("updatedMeasurements", updatedMeasurements);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "failure");
            response.put("message", "Record not found for the provided userid");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
