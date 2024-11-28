package com.example.iot_project_backserver.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class EMG_Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성
    private Long id;

    private String userid;

    private String EmgResult;
    @Temporal(TemporalType.DATE)
    private Date date;
}
