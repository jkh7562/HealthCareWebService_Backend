package com.example.iot_project_backserver.service;

import com.example.iot_project_backserver.service.CloudServiceInterface;
import org.springframework.stereotype.Service;

@Service
public class CloudService implements CloudServiceInterface {
    //클라우드 서버와 연결 및 데이터 수신 로직 구현

    @Override
    public String connectToCloud(boolean useWebSocket) {
        //클라우드 서버에 연결하는 로직 구현
        return "Connected to Cloud";
    }
}
