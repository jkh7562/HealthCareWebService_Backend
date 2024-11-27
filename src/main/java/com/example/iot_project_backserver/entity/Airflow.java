package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Airflow")
@Getter
@Setter
public class Airflow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid;

    @ElementCollection
    @CollectionTable(name = "airflow_airflowdata", joinColumns = @JoinColumn(name = "airflow_id"))
    @Column(name = "airflowdata")
    private List<Float> airflowdata;

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "airflow_averages", joinColumns = @JoinColumn(name = "airflow_id"))
    private List<AirflowAverage> averages;
}

