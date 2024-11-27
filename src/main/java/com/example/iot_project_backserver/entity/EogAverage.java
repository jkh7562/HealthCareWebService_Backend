package com.example.iot_project_backserver.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class EogAverage {
    private Float averageValue; // 평균값
    private String userid; // 사용자 ID
}
