package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ECG") // 사용할 테이블 이름 작성
@Getter
@Setter
public class ECG {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성
        private Long id;

        @Column(name = "userId")
        private String userId; // 사용자 ID

        @ElementCollection
        @CollectionTable(name = "ecg_ecgdata", joinColumns = @JoinColumn(name = "ecg_id"))
        @Column(name = "ecgdata")
        private List<Float> ecgdata; // 측정 데이터

        private String device_id;

        @ElementCollection
        @CollectionTable(name = "ecg_everages", joinColumns = @JoinColumn(name = "ecg_id"))
        private List<EcgAverage> averages;//평균값 리스트 저장
}
