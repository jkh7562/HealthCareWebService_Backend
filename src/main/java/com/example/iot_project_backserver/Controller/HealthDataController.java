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

    /*@PostMapping("/airflow")
    public ResponseEntity<String> saveAirflow(@RequestBody Airflow airflow) { //TODO 이후 valid 이용해서 검증 기증 추가하기
        modelDataServiceImpl.createAirflowDataCSV(airflow);
        healthDataService.processAndSaveAirflowData(airflow);
        return ResponseEntity.ok("Airflow data processed and saved successfully.");
    }*/
    @PostMapping("/airflow")
    public ResponseEntity<String> saveAirflowData(@RequestBody Airflow airflow) {
        // CSV 파일 생성
        modelDataService.createAirflowDataCSV(airflow);

        // Airflow 데이터 처리 및 저장
        healthDataService.processAndSaveAirflowData(airflow);

        try {
            // FastAPI 호출 및 응답 처리
            Map<String, Object> responseBody = healthDataService.callFastAPIAirFlow(airflow);

            // FastAPI 응답에서 데이터 추출
            String airflowResult = (String) responseBody.get("airflowresult");

            // Airflow_Result 엔터티 생성
            AirFlow_Result airflowResultEntity = new AirFlow_Result();
            airflowResultEntity.setUserid(airflow.getUserid());
            airflowResultEntity.setAirFlowResult(airflowResult);
            airflowResultEntity.setDate(new Date());

            // 저장 또는 업데이트
            healthDataService.saveOrUpdateAirflowResult(airflowResultEntity);

            return ResponseEntity.ok("FastAPI result: " + responseBody);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing Airflow data: " + e.getMessage());
        }
    }


    @GetMapping("/airflow")
    public ResponseEntity<List<Airflow>> getAllAirflowData() {
        return ResponseEntity.ok(healthDataService.getAllAirflowData());
    }

    /*@PostMapping("/bodytemp")
    public ResponseEntity<BodyTemp> saveBodyTemp(@RequestBody BodyTemp bodyTemp) {
        return ResponseEntity.ok(healthDataService.saveBodyTempData(bodyTemp));
    }*/
    /*@PostMapping("/bodytemp")
    public ResponseEntity<String> saveBodyTemp(@RequestBody BodyTemp bodyTemp) {
        try {
            healthDataService.saveOrUpdateBodyTempResult(bodyTemp);
            return ResponseEntity.ok("Body temperature data processed and saved successfully.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing BodyTemp data: " + e.getMessage());
        }
    }*/
    @PostMapping("/bodytemp")
    public ResponseEntity<String> saveBodyTemp(@RequestBody BodyTemp bodyTemp) {
        try {
            // BodyTemp 테이블에 데이터 저장
            healthDataService.saveBodyTempData(bodyTemp);

            // BodyTemp_Result 테이블에 데이터 저장 또는 업데이트
            healthDataService.saveOrUpdateBodyTempResult(bodyTemp);

            return ResponseEntity.ok("Body temperature data processed and saved successfully.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing BodyTemp data: " + e.getMessage());
        }
    }



    @GetMapping("/bodytemp")
    public ResponseEntity<List<BodyTemp>> getAllBodyTempData() {
        return ResponseEntity.ok(healthDataService.getAllBodyTempData());
    }

    /*@PostMapping("/nibp")
    public ResponseEntity<NIBP> saveNIBP(@RequestBody NIBP nibp) {
        return ResponseEntity.ok(healthDataService.saveNIBPData(nibp));
    }*/

    @PostMapping("/nibp")
    public ResponseEntity<String> saveNIBP(@RequestBody NIBP nibp) {
        try {
            // BodyTemp 테이블에 데이터 저장
            healthDataService.saveNIBPData(nibp);

            // BodyTemp_Result 테이블에 데이터 저장 또는 업데이트
            healthDataService.saveOrUpdateNIBPResult(nibp);

            return ResponseEntity.ok("NIBP data processed and saved successfully.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing NIBP data: " + e.getMessage());
        }
    }


    @PostMapping("/spo2")
    public ResponseEntity<String> saveSPO2(@RequestBody SPO2 spo2) {
        try {
            // SPO2 테이블에 데이터 저장
            healthDataService.saveSPO2Data(spo2);

            // SPO2_Result 테이블에 데이터 저장 또는 업데이트
            healthDataService.saveOrUpdateSPO2Result(spo2);

            return ResponseEntity.ok("SPO2 data processed and saved successfully.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing SPO2 data: " + e.getMessage());
        }
    }


    /*@PostMapping("/eog")
    public ResponseEntity<String> saveECGData(@RequestBody EOG eog) {
        modelDataServiceImpl.createEOGDataCSV(eog);
        healthDataService.processAndSaveEOGData(eog);
        return ResponseEntity.ok("EOG data processed and saved successfully.");
    }*/
    @PostMapping("/eog")
    public ResponseEntity<String> saveEOGData(@RequestBody EOG eog) {
        modelDataService.createEOGDataCSV(eog);
        healthDataService.processAndSaveEOGData(eog);
        try {
            // FastAPI 호출 및 응답 처리
            Map<String, Object> responseBody = healthDataService.callFastAPIEOG(eog);

            // FastAPI 응답에서 데이터 추출
            String eogResult = (String) responseBody.get("eogresult");

            // EOG_Result 엔터티 생성
            EOG_Result eogResultEntity = new EOG_Result();
            eogResultEntity.setUserid(eog.getUserid());
            eogResultEntity.setEogResult(eogResult);
            eogResultEntity.setDate(new Date());

            // 저장 또는 업데이트
            healthDataService.saveOrUpdateEOGResult(eogResultEntity);

            return ResponseEntity.ok("FastAPI result: " + responseBody);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing EOG data: " + e.getMessage());
        }
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
        // CSV 파일 생성
        modelDataService.createEMGDataCSV(emg);

        // EMG 데이터 처리 및 저장
        healthDataService.processAndSaveEMGData(emg);

        try {
            // FastAPI 호출 및 응답 처리
            Map<String, Object> responseBody = healthDataService.callFastAPIEMG(emg);

            // FastAPI 응답에서 데이터 추출
            String emgResult = (String) responseBody.get("emgresult");

            // EMG_Result 엔터티 생성
            EMG_Result emgResultEntity = new EMG_Result();
            emgResultEntity.setUserid(emg.getUserid());
            emgResultEntity.setEmgResult(emgResult);
            emgResultEntity.setDate(new Date());

            // 저장 또는 업데이트
            healthDataService.saveOrUpdateEMGResult(emgResultEntity);

            return ResponseEntity.ok("FastAPI result: " + responseBody);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing EMG data: " + e.getMessage());
        }
    }


    @PostMapping("/gsr")
    public ResponseEntity<String> saveGSRData(@RequestBody GSR gsr) {
        // CSV 파일 생성
        modelDataService.createGSRDataCSV(gsr);

        // GSR 데이터 처리 및 저장
        healthDataService.processAndSaveGSRData(gsr);

        try {
            // FastAPI 호출 및 응답 처리
            Map<String, Object> responseBody = healthDataService.callFastAPIGSR(gsr);

            // FastAPI 응답에서 데이터 추출
            String gsrResult = (String) responseBody.get("gsrresult");

            // GSR_Result 엔터티 생성
            GSR_Result gsrResultEntity = new GSR_Result();
            gsrResultEntity.setUserid(gsr.getUserid());
            gsrResultEntity.setGsrResult(gsrResult);
            gsrResultEntity.setDate(new Date());

            // 저장 또는 업데이트
            healthDataService.saveOrUpdateGSRResult(gsrResultEntity);

            return ResponseEntity.ok("FastAPI result: " + responseBody);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error processing GSR data: " + e.getMessage());
        }
    }


}
