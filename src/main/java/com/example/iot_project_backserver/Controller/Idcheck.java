package com.example.iot_project_backserver.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/idcheck")
public class Idcheck {

    @PostMapping
    public ResponseEntity<Map<String, String>> idcheck(@RequestParam("email") String email) {
        Map<String, String> response = new HashMap<>();

        // 이메일 ID 값을 콘솔에 출력
        System.out.println("Received email ID: " + email); // 추가된 부분

        try {
            // ID 사용 가능 여부 체크
            boolean isUsable = checkIdUsability(email); // ID 사용 가능 여부를 체크하는 로직

            if (isUsable) {
                response.put("status", "success");
                response.put("message", "이 ID 사용 가능합니다.");
            } else {
                response.put("status", "error");
                response.put("message", "이 ID 사용 불가합니다.");
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "오류가 발생했습니다: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // ID 사용 가능 여부를 체크하는 메서드 (예시)
    private boolean checkIdUsability(String email) {
        // 여기서 데이터베이스나 서비스와 연결하여 ID 사용 가능 여부를 확인합니다.
        // 아래는 예시로 특정 ID를 사용 불가로 설정
        return !email.equalsIgnoreCase("duplicate@example.com");
    }
}
