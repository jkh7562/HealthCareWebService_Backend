package com.example.iot_project_backserver.exception;


public class CustomException extends RuntimeException {

    //ID 유효성 검사를 위한 사용자 정의 예외
    public CustomException(String message) {
        super(message);
    }
}
