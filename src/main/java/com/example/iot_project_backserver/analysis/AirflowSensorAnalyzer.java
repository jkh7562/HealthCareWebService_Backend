package com.example.iot_project_backserver.analysis;

import java.util.ArrayList;
import java.util.List;

public class AirflowSensorAnalyzer {

    private static final float THRESHOLD_MULTIPLIER = 2.0f; // float 타입으로 변경
    private static final int SAMPLE_COUNT = (int) (5 / 0.002); // 5초 동안 필요한 데이터 개수 (2500개)

    // 평균 계산
    private static float calculateMean(List<Float> data) {
        float sum = 0.0f;
        for (float value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    // 표준편차 계산
    private static float calculateStandardDeviation(List<Float> data, float mean) {
        float sum = 0.0f;
        for (float value : data) {
            sum += Math.pow(value - mean, 2);
        }
        return (float) Math.sqrt(sum / data.size());
    }

    // 정상 범위 내 데이터인지 확인
    public static boolean isWithinNormalRange(List<Float> data) {
        if (data.size() < SAMPLE_COUNT) {
            throw new IllegalArgumentException("데이터가 부족합니다. 5초 분량의 데이터를 수집해야 합니다.");
        }

        // 5초 데이터만 사용하기 위해 최근 2500개의 데이터 추출
        List<Float> recentData = data.subList(data.size() - SAMPLE_COUNT, data.size());

        float mean = calculateMean(recentData);
        float stdDev = calculateStandardDeviation(recentData, mean);

        float lowerBound = mean - THRESHOLD_MULTIPLIER * stdDev;
        float upperBound = mean + THRESHOLD_MULTIPLIER * stdDev;

        for (float value : recentData) {
            if (value < lowerBound || value > upperBound) {
                return false; // 정상 범위를 벗어난 데이터 발견
            }
        }
        return true; // 모든 데이터가 정상 범위 내에 있음
    }

    public static void main(String[] args) {
        // 예제 데이터 생성
        List<Float> airflowData = new ArrayList<>();

        // 임의의 데이터 추가 (예: 10초 동안 수집된 데이터)
        for (int i = 0; i < SAMPLE_COUNT * 2; i++) { // 10초 데이터 생성 (0.002초마다 수집)
            airflowData.add((float) (Math.random() * 10 + 10)); // 예시: 10 ~ 20 범위의 임의 데이터
        }

        // 정상 범위 여부 검사
        if (isWithinNormalRange(airflowData)) {
            System.out.println("호흡 데이터가 정상 범위 내에 있습니다.");
        } else {
            System.out.println("호흡 데이터가 비정상입니다.");
        }
    }
}

