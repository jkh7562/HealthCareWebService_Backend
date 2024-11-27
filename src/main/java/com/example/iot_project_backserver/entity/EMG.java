package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EMG")
@Getter
@Setter
public class EMG {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userid;

    @ElementCollection
    @CollectionTable(name = "emg_emgdata", joinColumns = @JoinColumn(name = "emg_id"))
    @Column(name = "emgdata")
    private List<Float> emgdata;

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "emg_averages", joinColumns = @JoinColumn(name = "emg_id"))
    private List<EmgAverage> averages;
}

