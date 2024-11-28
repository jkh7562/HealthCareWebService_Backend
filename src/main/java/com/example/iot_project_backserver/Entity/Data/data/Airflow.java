package com.example.iot_project_backserver.Entity.Data.data;

import com.example.iot_project_backserver.Entity.Data.Average.AirflowAverage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Airflow") // 사용할 테이블 이름 작성
@Getter
@Setter
public class Airflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성
    private Long id;

    private String userid; // 사용자 ID

    @ElementCollection
    @CollectionTable(name = "airflow_airflowdata", joinColumns = @JoinColumn(name = "airflow_id"))
    @Column(name = "airflowdata")
    private List<Float> airflowdata; // 측정 데이터

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "airflow_averages", joinColumns = @JoinColumn(name = "airflow_id"))
    @AttributeOverrides({
            @AttributeOverride(name = "AirflowAverageValue", column = @Column(name = "AirflowAverageValue")),
            @AttributeOverride(name = "userid", column = @Column(name = "userid"))
    })
    private List<AirflowAverage> averages;
}
