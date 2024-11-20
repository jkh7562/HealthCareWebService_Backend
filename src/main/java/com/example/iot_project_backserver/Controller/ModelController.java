package com.example.iot_project_backserver.Controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ModelController {

    private final String FASTAPI_URL = "http://127.0.0.1:8000/predict";

    @PostMapping("/sendData")
    public ResponseEntity<Object> sendDataToFastAPI(@RequestBody float[][] inputData) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 데이터 생성
        Map<String, Object> requestData = new HashMap<>();
        requestData.put("inputData", inputData);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 엔터티 생성
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestData, headers);

        // FastAPI로 POST 요청 보내기
        ResponseEntity<Object> response = restTemplate.postForEntity(FASTAPI_URL, request, Object.class);

        // FastAPI 응답 반환
        return ResponseEntity.ok(response.getBody());
    }
}