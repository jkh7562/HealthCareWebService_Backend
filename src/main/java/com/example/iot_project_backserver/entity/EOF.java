package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EOF")
@Getter
@Setter
public class EOF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    @ElementCollection
    @CollectionTable(name = "eof_eofdata", joinColumns = @JoinColumn(name = "eof_id"))
    @Column(name = "eofdata")
    private List<Float> eofdata;

    private String device_id;

    @ElementCollection
    @CollectionTable(name = "eof_averages", joinColumns = @JoinColumn(name = "eof_id"))
    private List<EofAverage> averages;
}


