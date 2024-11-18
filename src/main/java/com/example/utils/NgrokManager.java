package com.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class NgrokManager {

    public static String startNgrok() {
        try {
            // ngrok 실행
            String ngrok = "C:/ngrok.exe";
            ProcessBuilder processBuilder = new ProcessBuilder("ngrok", "http", "8081");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // ngrok URL 추출
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("https://")) {  // ngrok URL 패턴 확인
                    return line.substring(line.indexOf("https://")).split(" ")[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
