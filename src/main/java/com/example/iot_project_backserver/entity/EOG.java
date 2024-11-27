package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EOG") // 사용할 테이블 이름 작성
@Getter
@Setter
public class EOG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성
    private Long id;

    private String userid; // 사용자 ID

    @ElementCollection
    @CollectionTable(name = "eog_eogdata", joinColumns = @JoinColumn(name = "eog_id"))
    @Column(name = "eogdata")
    private List<Float> eogdata; // 측정 데이터

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "eog_averages", joinColumns = @JoinColumn(name = "eog_id"))
    private List<EogAverage> averages; // 평균값 리스트 저장
}
