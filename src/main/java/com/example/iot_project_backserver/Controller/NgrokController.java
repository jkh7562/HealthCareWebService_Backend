/*
package com.example.iot_project_backserver.Controller;

import com.example.utils.NgrokManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NgrokController {

    private String ngrokUrl;

    public NgrokController() {
        // ngrok URL 초기화
        this.ngrokUrl = NgrokManager.startNgrok();
    }

    @GetMapping("/ngrok-url")
    public String getNgrokUrl() {
        return ngrokUrl != null ? ngrokUrl : "ngrok not running.";
    }
}*/
