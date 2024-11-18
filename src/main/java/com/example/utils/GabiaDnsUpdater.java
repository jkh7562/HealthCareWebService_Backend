/*
package com.example.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class GabiaDnsUpdater {

    private static final String GABIA_API_URL = "https://api.gabia.com/dns/v1/zones";
    private static final String API_KEY = "YOUR_GABIA_API_KEY";

    public void updateDnsRecord(String ngrokUrl) {
        RestTemplate restTemplate = new RestTemplate();
        // 가비아 DNS 레코드 요청 본문 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("host", "www");
        requestBody.put("type", "CNAME");
        requestBody.put("value", ngrokUrl);

        // API 호출
        restTemplate.put(GABIA_API_URL, requestBody, API_KEY);
    }
}
*/
//가비아 설정할때 만들었던 클래스 쓸모 없을경우 삭제하기
