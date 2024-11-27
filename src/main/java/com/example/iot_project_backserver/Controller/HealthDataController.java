package com.example.iot_project_backserver.Controller;

import com.example.iot_project_backserver.entity.*;
import com.example.iot_project_backserver.service.HealthDataService;
import com.example.iot_project_backserver.service.ModelDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.iot_project_backserver.service.ModelData;
import org.springframework.web.client.RestTemplate;

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

    @PostMapping("/nibp")
    public ResponseEntity<NIBP> saveNIBP(@RequestBody NIBP nibp) {
        return ResponseEntity.ok(healthDataService.saveNIBPData(nibp));
    }

    @PostMapping("/spo2")
    public ResponseEntity<SPO2> saveSPO2(@RequestBody SPO2 spo2) {
        return ResponseEntity.ok(healthDataService.saveSPO2(spo2));
    }

    @PostMapping("/eog")
    public ResponseEntity<String> saveECGData(@RequestBody EOG eog) {
        modelDataService.createEOGDataCSV(eog);
        healthDataService.processAndSaveEOGData(eog);
        return ResponseEntity.ok("EOG data processed and saved successfully.");
    }

    @GetMapping("/eog")
    public ResponseEntity<List<EOG>> getAllEogData() {return ResponseEntity.ok(healthDataService.getAllEogData());
    }

    /*@PostMapping("/ecg")
    public ResponseEntity<String> saveECGData(@RequestBody ECG ecg) {
        modelDataService.createECGDataCSV(ecg);
        healthDataService.processAndSaveECGData(ecg);
        return ResponseEntity.ok("ECG data processed and saved successfully.");
    }*/

    @PostMapping("/ecg")
    public ResponseEntity<String> saveECGData(@RequestBody ECG ecg) {
        try {
            // FastAPI로 전송할 JSON 데이터 준비
            Map<String, Object> payload = new HashMap<>();
            System.out.println("유저아이디 표시 부분:" + ecg.getUserId());
            payload.put("userid", ecg.getUserId()); // FastAPI가 기대하는 필드명
            payload.put("ecgdata", ecg.getEcgdata()); // FastAPI가 기대하는 필드명
            //System.out.println("json 파일 형식 확인하기 !Payload to FastAPI: " + payload);

            // FastAPI 서버 URL
            String fastApiUrl = "http://127.0.0.1:8082/predict";

            // RestTemplate 생성 및 JSON Content-Type 설정
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // 요청 바디 생성
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            // POST 요청 전송
            ResponseEntity<Map> response = restTemplate.postForEntity(fastApiUrl, request, Map.class);

            // FastAPI의 응답 처리
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                // FastAPI 응답 출력
                System.out.println("FastAPI Response: " + response.getBody());
                return ResponseEntity.ok("FastAPI result: " + response.getBody());
            } else {
                System.out.println("FastAPI call failed: " + response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body("FastAPI call failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing ECG data");
        }
    }



    @PostMapping("/emg")
    public ResponseEntity<String> saveEMGData(@RequestBody EMG emg) {
        modelDataService.createEMGDataCSV(emg);
        healthDataService.processAndSaveEMGData(emg);
        return ResponseEntity.ok("EMG data processed and saved successfully.");
    }

    @PostMapping("/gsr")
    public ResponseEntity<String> saveGSRData(@RequestBody GSR gsr) {
        modelDataService.createGSRDataCSV(gsr);
        healthDataService.processAndSaveGSRData(gsr);
        return ResponseEntity.ok("GSR data processed and saved successfully.");
    }

}
