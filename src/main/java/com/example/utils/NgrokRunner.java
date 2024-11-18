package com.example.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NgrokRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        String ngrokUrl = NgrokManager.startNgrok();
        if (ngrokUrl != null) {
            System.out.println("ngrok URL: " + ngrokUrl);
        } else {
            System.out.println("Failed to start ngrok.");
        }
    }
}
