package com.example.iot_project_backserver.Controller;

import com.example.iot_project_backserver.entity.Airflow;
import com.example.iot_project_backserver.entity.BodyTemp;
import com.example.iot_project_backserver.entity.ECG;
import com.example.iot_project_backserver.entity.Eog;
import com.example.iot_project_backserver.service.HealthDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ws")
public class HealthDataController {

    private final HealthDataService healthDataService;

    @Autowired
    public HealthDataController(HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    @PostMapping("/airflow")
    public ResponseEntity<Airflow> saveAirflow(@RequestBody Airflow airflow) { //TODO 이후 valid 이용해서 검증 기증 추가하기
        return ResponseEntity.ok(healthDataService.saveAirflow(airflow));
    }

    @GetMapping("/airflow")
    public ResponseEntity<List<Airflow>> getAllAirflowData() {
        return ResponseEntity.ok(healthDataService.getAllAirflowData());
    }

    @PostMapping("/bodytemp")
    public ResponseEntity<BodyTemp> saveBodyTemp(@RequestBody BodyTemp bodyTemp) {
        return ResponseEntity.ok(healthDataService.saveBodyTempData(bodyTemp));
    }

    @GetMapping("/bodytemp")
    public ResponseEntity<List<BodyTemp>> getAllBodyTempData() {
        return ResponseEntity.ok(healthDataService.getAllBodyTempData());
    }

    @PostMapping("/eog")
    public ResponseEntity<Eog> saveEogData(@RequestBody Eog eog) {
        return ResponseEntity.ok(healthDataService.saveEogData(eog));
    }

    @GetMapping("/eog")
    public ResponseEntity<List<Eog>> getAllEogData() {return ResponseEntity.ok(healthDataService.getAllEogData());
    }

    @PostMapping("/ecg")
    public ResponseEntity<ECG> saveECGData(@RequestBody ECG ecg) {
        return ResponseEntity.ok(healthDataService.saveECGData(ecg));
    }
}
