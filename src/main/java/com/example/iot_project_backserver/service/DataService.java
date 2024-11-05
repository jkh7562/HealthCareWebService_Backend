/*
package com.example.iot_project_backserver.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@Service
public class DataService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "https://port-0-iot-healthcare-1272llwukgaeg.sel5.cloudtype.app/";

    @PostConstruct
    public void init() {
        testConnection();  // 서버 시작 시 자동으로 호출
    }

    public String testConnection() {
        try {
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("서버 연결 성공: " + response);
            return "서버 연결 성공: " + response;
        } catch (HttpClientErrorException e) {
            System.err.println("서버 응답 오류: " + e.getStatusCode());
            return "서버 응답 오류: " + e.getStatusCode();
        } catch (ResourceAccessException e) {
            System.err.println("서버에 연결할 수 없습니다: " + e.getMessage());
            return "서버에 연결할 수 없습니다: " + e.getMessage();
        } catch (Exception e) {
            System.err.println("예기치 않은 오류 발생: " + e.getMessage());
            return "예기치 않은 오류 발생: " + e.getMessage();
        }
    }
}
*/
