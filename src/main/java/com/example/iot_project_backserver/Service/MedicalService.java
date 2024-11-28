package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.User.required_measurements;
import com.example.iot_project_backserver.Entity.Medical.patient_assignment;

import java.util.List;
import java.util.Optional;

public interface MedicalService {
    // LoadMeasureService의 메서드
    required_measurements checkAndInsert(String userid);
    required_measurements updateMeasurements(String userid, String airflow, String bodytemp, String nibp, String spo2, String ecg, String emg, String gsr);

    // PatientAssignmentService의 메서드
    boolean checkAssignmentExists(String medicalid, String userid);
    patient_assignment saveAssignment(String medicalid, String userid);
    List<patient_assignment> findAssignmentsByMedicalid(String medicalid);
    boolean deletePatientAssignment(String medicalid, String userid);
    Optional<patient_assignment> findByUserid(String userid);
}
