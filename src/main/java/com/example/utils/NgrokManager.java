package com.example.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NgrokManager {

    public static void startNgrok() {
        try {
            // ngrok 실행 경로 (환경에 맞게 수정)
            //String ngrokPath = "/opt/homebrew/bin/ngrok"; // 실제 ngrok 실행 파일 경로

            // ProcessBuilder로 명령어 실행
            ProcessBuilder processBuilder = new ProcessBuilder("http", "--url=reptile-promoted-publicly.ngrok-free.app", "8081"
            );
            processBuilder.redirectErrorStream(true); // 오류와 출력을 통합
            Process process = processBuilder.start();

            // 실행 결과 출력
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("NGROK OUTPUT: " + line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
