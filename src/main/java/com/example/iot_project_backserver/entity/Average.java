/*package com.example.iot_project_backserver.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Average") // 사용할 테이블 이름 작성
@Getter
@Setter
public class Average {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userid;

    @ElementCollection
    @CollectionTable(name = "ecg_everages", joinColumns = @JoinColumn(name = "ecg_id"))
    private List<EcgAverage> ecgaverages;//평균값 리스트 저장

    @ElementCollection
    @CollectionTable(name = "airflow_averages", joinColumns = @JoinColumn(name = "airflow_id"))
    private List<AirflowAverage> airflowaverages;

    @ElementCollection
    @CollectionTable(name = "emg_averages", joinColumns = @JoinColumn(name = "emg_id"))
    private List<EmgAverage> emgaverages;

    @ElementCollection
    @CollectionTable(name = "eog_averages", joinColumns = @JoinColumn(name = "eog_id"))
    private List<EogAverage> averages; // 평균값 리스트 저장
}*/
