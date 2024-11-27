package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "GSR")
@Getter
@Setter
public class GSR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid;

    @ElementCollection
    @CollectionTable(name = "gsr_gsrdata", joinColumns = @JoinColumn(name = "gsr_id"))
    @Column(name = "gsrdata")
    private List<Float> gsrdata;

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "gsr_averages", joinColumns = @JoinColumn(name = "gsr_id"))
    private List<GsrAverage> averages;
}

