package com.example.iot_project_backserver;

import org.apache.poi.util.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//임시 임퐅트(엑셀 파일 랜덤 생성을 위함)


@SpringBootApplication
public class BackServer {
    public static void main(String[] args) {
        //생성 데이터셋 엑셀 파일 용량 제한 1GB
        IOUtils.setByteArrayMaxOverride(1024 * 1024 * 1024);

        SpringApplication.run(BackServer.class, args);
    }
}
