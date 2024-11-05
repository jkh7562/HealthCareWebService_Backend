package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Eog")
@Getter
@Setter
public class Eog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;
    @ElementCollection
    private List<Float> eogdata;



}
