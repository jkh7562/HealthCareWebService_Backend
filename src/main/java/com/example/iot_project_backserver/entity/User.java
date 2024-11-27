package com.example.iot_project_backserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users") // 테이블 이름
@Getter
@Setter
public class User {

    @Id
    @Column(name = "userid", length = 255) // VARCHAR(255)
    private String userid; // 사용자 ID, PRIMARY KEY

    @Column(name = "password", length = 255, nullable = false) // NOT NULL
    private String password; // 비밀번호

    @Column(name = "name", length = 255, nullable = false) // NOT NULL
    private String name; // 사용자 이름

    @Column(name = "date_of_birth", nullable = false) // NOT NULL
    @Temporal(TemporalType.DATE) // DATE 타입
    private Date dateOfBirth; // 생년월일

    @Column(name = "phone_num", length = 20) // 전화번호
    private String phoneNum; // 전화번호

    @Enumerated(EnumType.STRING) // ENUM 타입
    @Column(name = "division", nullable = false) // NOT NULL
    private UserDivision division; // 사용자 구분

    // ENUM 정의
    public enum UserDivision {
        PATIENT,
        VOLUNTEER,
        MEDICAL
    }
}
