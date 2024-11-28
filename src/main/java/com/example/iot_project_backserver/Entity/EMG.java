package com.example.iot_project_backserver.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EMG") // 사용할 테이블 이름 작성
@Getter
@Setter
public class EMG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성
    private Long id;

    private String userid; // 사용자 ID

    @ElementCollection
    @CollectionTable(name = "emg_emgdata", joinColumns = @JoinColumn(name = "emg_id"))
    @Column(name = "emgdata")
    private List<Float> emgdata; // 측정 데이터

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "emg_averages", joinColumns = @JoinColumn(name = "emg_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "EmgAverageValue", column = @Column(name = "EmgAverageValue")),
            @AttributeOverride(name = "userid", column = @Column(name = "userid"))
    })
    private List<EmgAverage> averages;
}
