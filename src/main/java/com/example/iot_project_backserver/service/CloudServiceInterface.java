package com.example.iot_project_backserver.service;

public interface CloudServiceInterface {
    String connectToCloud(boolean useWebSocket);
    // 추가적인 클라우드 관련 메서드 정의 가능
}
