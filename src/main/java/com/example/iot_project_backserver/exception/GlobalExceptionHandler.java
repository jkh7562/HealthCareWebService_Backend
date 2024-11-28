package com.example.iot_project_backserver.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//전역 예외 처리기
@ControllerAdvice
public class GlobalExceptionHandler {


    //유효한 ID가 아닐 경우, 클라이언트는 400 Bad Request 상태와 함께 예외 메시지를 받게 됩니다. 클라이언트는 이 메시지를 통해 유효하지 않은 ID라는 정보를 얻을 수 있습니다.
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}