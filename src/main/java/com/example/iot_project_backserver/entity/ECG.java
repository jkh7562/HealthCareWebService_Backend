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

        private String userid; // 사용자 ID

        @ElementCollection
        @CollectionTable(name = "ecg_ecgdata", joinColumns = @JoinColumn(name = "ecg_id"))
        @Column(name = "ecgdata")
        private List<Float> ecgdata; // 측정 데이터

        private String device_id;

        @ElementCollection
        @CollectionTable(name = "ecg_averages", joinColumns = @JoinColumn(name = "ecg_id"))
        @AttributeOverrides({
                @AttributeOverride(name = "EcgAverageValue", column = @Column(name = "EcgAveragevalue")),
                @AttributeOverride(name = "userid", column = @Column(name = "userid"))
        })
        private List<EcgAverage> averages;
}


