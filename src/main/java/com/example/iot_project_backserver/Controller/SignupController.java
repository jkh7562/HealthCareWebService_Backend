package com.example.iot_project_backserver.Controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/signup")
public class SignupController {

    @PostMapping
    public ResponseEntity<Map<String, String>> createNewUser(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("username") String username,
            @RequestParam("birth") String birth,
            @RequestParam("phoneNum") String phoneNum,
            @RequestParam("role") String role,
            @RequestParam(value = "userImage", required = false) MultipartFile userImage) {

        // 각 값을 변수에 저장
        String userEmail = email;
        String userPassword = password;
        String userName = username;
        String userBirth = birth;
        String userPhoneNum = phoneNum;
        String userRole = role;



        // userImage는 MultipartFile 타입이므로 추가적인 처리가 필요할 수 있음
        if (userImage != null) {
            String userImageName = userImage.getOriginalFilename(); // 이미지 파일 이름
            System.out.println("Uploaded Image Name: " + userImageName);
        }

        // 수신한 데이터를 콘솔에 출력
        System.out.println("Email: " + userEmail);
        System.out.println("Password: " + userPassword);
        System.out.println("Username: " + userName);
        System.out.println("Birth: " + userBirth);
        System.out.println("PhoneNum: " + userPhoneNum);
        System.out.println("Role: " + userRole);

        // 응답을 위한 맵 생성s
        Map<String, String> response = new HashMap<>();
        response.put("EC", "0");  // EC가 0이면 성공
        response.put("EM", "User created successfully");  // 성공 메시지

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
