package com.example.iot_project_backserver.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "GSR") // 사용할 테이블 이름 작성
@Getter
@Setter
public class GSR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성
    private Long id;

    private String userid; // 사용자 ID

    @ElementCollection
    @CollectionTable(name = "gsr_gsrdata", joinColumns = @JoinColumn(name = "gsr_id"))
    @Column(name = "gsrdata")
    private List<Float> gsrdata; // 측정 데이터

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "gsr_averages", joinColumns = @JoinColumn(name = "gsr_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "GsrAverageValue", column = @Column(name = "GsrAverageValue")),
            @AttributeOverride(name = "userid", column = @Column(name = "userid"))
    })
    private List<GsrAverage> averages;
}
