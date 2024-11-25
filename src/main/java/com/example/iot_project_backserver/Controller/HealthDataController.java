package com.example.iot_project_backserver.Controller;

import com.example.iot_project_backserver.entity.Airflow;
import com.example.iot_project_backserver.entity.BodyTemp;
import com.example.iot_project_backserver.entity.ECG;
import com.example.iot_project_backserver.entity.Eog;
import com.example.iot_project_backserver.service.HealthDataService;
import com.example.iot_project_backserver.service.ModelDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.iot_project_backserver.service.ModelData;

@Controller
@RestController
@RequestMapping("/ws")
public class HealthDataController {

    private final HealthDataService healthDataService;
    //임시 데이터 위함

    @Autowired
    public HealthDataController(HealthDataService healthDataService) {
        this.healthDataService = healthDataService;
    }

    @Autowired
    public ModelDataService modelDataService;

    @PostMapping("/airflow")
    public ResponseEntity<String> saveAirflow(@RequestBody Airflow airflow) { //TODO 이후 valid 이용해서 검증 기증 추가하기
        modelDataService.createAirflowDataCSV(airflow);
        healthDataService.processAndSaveAirflowData(airflow);
        return ResponseEntity.ok("Airflow data processed and saved successfully.");
    }

    @GetMapping("/airflow")
    public ResponseEntity<List<Airflow>> getAllAirflowData() {
        return ResponseEntity.ok(healthDataService.getAllAirflowData());s
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
    public ResponseEntity<String> saveECGData(@RequestBody ECG ecg) {
        modelDataService.createECGDataCSV(ecg);
        healthDataService.processAndSaveECGData(ecg);
        return ResponseEntity.ok("ECG data processed and saved successfully.");
    }

}
