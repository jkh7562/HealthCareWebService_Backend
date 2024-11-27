package com.example.iot_project_backserver.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Data
public class BodyTemp_Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성
    private Long id;

    private String userid;

    private String BodyTempResult;
    @Temporal(TemporalType.DATE)
    private Date date;
}
