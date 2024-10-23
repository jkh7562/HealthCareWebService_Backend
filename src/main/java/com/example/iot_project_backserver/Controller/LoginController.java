package com.example.iot_project_backserver.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    //@Autowired
    //private UserService userService; // 사용할 로그인 인증 기능에 대한 서비스 클래스

    @PostMapping
    public ResponseEntity<Map<String, String>> login(@RequestParam("email") String email,
                                                     @RequestParam("password") String password) {
        Map<String, String> response = new HashMap<>();

        // 사용자가 존재하는지 확인 하는 서비스 층의 로직 이용
       // boolean isAuthenticated = userService.authenticate(email, password);

        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        boolean isAuthenticated = true; // 리스폰을 위한 임시 변수

        if (isAuthenticated) {
            response.put("message", "로그인 성공");
            // 추가적인 정보(예: JWT 토큰)도 여기에 추가할 수 있습니다.
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}