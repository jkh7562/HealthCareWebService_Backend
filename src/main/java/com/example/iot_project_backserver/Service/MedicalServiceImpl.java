package com.example.iot_project_backserver.Service;

import com.example.iot_project_backserver.Entity.User.required_measurements;
import com.example.iot_project_backserver.Entity.Medical.patient_assignment;
import com.example.iot_project_backserver.Repository.Medical.RequiredMeasurementsRepository;
import com.example.iot_project_backserver.Repository.Medical.PatientAssignmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicalServiceImpl implements MedicalService {

    @Autowired
    private RequiredMeasurementsRepository requiredMeasurementsRepository;

    @Autowired
    private PatientAssignmentRepository patientAssignmentRepository;

    // LoadMeasureService 관련 메서드 구현
    @Override
    public required_measurements checkAndInsert(String userid) {
        Optional<required_measurements> existingRecord = requiredMeasurementsRepository.findById(userid);
        if (existingRecord.isEmpty()) {
            required_measurements newRecord = required_measurements.builder()
                    .userid(userid)
                    .airflow("false")
                    .bodytemp("false")
                    .nibp("false")
                    .spo2("false")
                    .ecg("false")
                    .emg("false")
                    .gsr("false")
                    .build();
            return requiredMeasurementsRepository.save(newRecord);
        }
        return existingRecord.get();
    }

    @Override
    public required_measurements updateMeasurements(String userid, String airflow, String bodytemp, String nibp, String spo2, String ecg, String emg, String gsr) {
        Optional<required_measurements> existingRecord = requiredMeasurementsRepository.findById(userid);
        if (existingRecord.isPresent()) {
            required_measurements measurements = existingRecord.get();
            measurements.setAirflow(airflow);
            measurements.setBodytemp(bodytemp);
            measurements.setNibp(nibp);
            measurements.setSpo2(spo2);
            measurements.setEcg(ecg);
            measurements.setEmg(emg);
            measurements.setGsr(gsr);
            return requiredMeasurementsRepository.save(measurements);
        }
        return null;
    }

    // PatientAssignmentService 관련 메서드 구현
    @Override
    public boolean checkAssignmentExists(String medicalid, String userid) {
        return patientAssignmentRepository.existsByMedicalidAndUserid(medicalid, userid);
    }

    @Override
    public patient_assignment saveAssignment(String medicalid, String userid) {
        patient_assignment newAssignment = patient_assignment.builder()
                .medicalid(medicalid)
                .userid(userid)
                .build();
        return patientAssignmentRepository.save(newAssignment);
    }

    @Override
    public List<patient_assignment> findAssignmentsByMedicalid(String medicalid) {
        return patientAssignmentRepository.findByMedicalid(medicalid);
    }

    @Transactional
    @Override
    public boolean deletePatientAssignment(String medicalid, String userid) {
        patient_assignment assignment = patientAssignmentRepository.findByMedicalidAndUserid(medicalid, userid);
        if (assignment != null) {
            patientAssignmentRepository.delete(assignment);
            // required_measurements 테이블에서 일치하는 userid의 레코드 삭제
            requiredMeasurementsRepository.deleteByUserid(userid);
            return true;
        }
        return false;
    }
    @Override
    public Optional<patient_assignment> findByUserid(String userid) {
        return patientAssignmentRepository.findByUserid(userid);
    }
}
