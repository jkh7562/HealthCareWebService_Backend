package com.example.iot_project_backserver.Controller;

import com.example.iot_project_backserver.entity.*;
import com.example.iot_project_backserver.service.HealthDataService;
import com.example.iot_project_backserver.service.ModelDataService;
import com.example.iot_project_backserver.service.ModelDataServiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.client.RestTemplate;

@Controller
@RestController
@RequestMapping("/ws")
public class HealthDataController {

    private final HealthDataService healthDataService;
    private final ModelDataService modelDataService;
    //임시 데이터 위함

    @Autowired
    public HealthDataController(HealthDataService healthDataService,
                                ModelDataService modelDataService) {
        this.healthDataService = healthDataService;
        this.modelDataService = modelDataService;
    }

    @Autowired
    public ModelDataServiceServiceImpl modelDataServiceImpl;

    @PostMapping("/airflow")
    public ResponseEntity<String> saveAirflow(@RequestBody Airflow airflow) { //TODO 이후 valid 이용해서 검증 기증 추가하기
        modelDataServiceImpl.createAirflowDataCSV(airflow);
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
        modelDataServiceImpl.createEOGDataCSV(eog);
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

    /*@PostMapping("/ecg")
    public ResponseEntity<String> saveECGData(@RequestBody ECG ecg) {
        modelDataService.createECGDataCSV(ecg);
        healthDataService.processAndSaveECGData(ecg);
        try {
            // FastAPI로 전송할 JSON 데이터 준비
            Map<String, Object> payload = new HashMap<>();
            //System.out.println("유저아이디 표시 부분:" + ecg.getUserid());
            payload.put("userid", ecg.getUserid()); // FastAPI가 기대하는 필드명
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
                // 응답결과 저장하기
                Map<String, Object> responseBody = response.getBody();
                //ecg 결과 추출하기
                String userid = (String) responseBody.get("userid");
                String ecgResult = (String) responseBody.get("ecgresult");
                System.out.println("FastAPI Response: " + ecgResult);
                //ECG_Result 엔티티에 저장
                ECG_Result ecgresult = new ECG_Result();
                ecgresult.setUserid(ecg.getUserid());
                ecgresult.setEcgResult(ecgResult);
                ecgresult.setDate(new Date());

                healthDataService.saveOrUpdateECGResult(ecgresult);

                return ResponseEntity.ok("FastAPI result: " + response.getBody());
            } else {
                System.out.println("FastAPI call failed: " + response.getStatusCode());
                return ResponseEntity.status(response.getStatusCode()).body("FastAPI call failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing ECG data");
        }
    }*/
    @PostMapping("/ecg")
    public ResponseEntity<String> saveECGData(@RequestBody ECG ecg) {
        modelDataService.createECGDataCSV(ecg);
        healthDataService.processAndSaveECGData(ecg);
        try {
            // FastAPI 호출 및 응답 처리
            Map<String, Object> responseBody = healthDataService.callFastAPIECG(ecg);

            // FastAPI 응답에서 데이터 추출
            String ecgResult = (String) responseBody.get("ecgresult");

            // ECG_Result 엔터티 생성
            ECG_Result ecgResultEntity = new ECG_Result();
            ecgResultEntity.setUserid(ecg.getUserid());
            ecgResultEntity.setEcgResult(ecgResult);
            ecgResultEntity.setDate(new Date());

            // 저장 또는 업데이트
            healthDataService.saveOrUpdateECGResult(ecgResultEntity);

            return ResponseEntity.ok("FastAPI result: " + responseBody);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing ECG data: " + e.getMessage());
        }
    }





    @PostMapping("/emg")
    public ResponseEntity<String> saveEMGData(@RequestBody EMG emg) {
        modelDataServiceImpl.createEMGDataCSV(emg);
        healthDataService.processAndSaveEMGData(emg);
        return ResponseEntity.ok("EMG data processed and saved successfully.");
    }

    @PostMapping("/gsr")
    public ResponseEntity<String> saveGSRData(@RequestBody GSR gsr) {
        modelDataServiceImpl.createGSRDataCSV(gsr);
        healthDataService.processAndSaveGSRData(gsr);
        return ResponseEntity.ok("GSR data processed and saved successfully.");
    }

}
