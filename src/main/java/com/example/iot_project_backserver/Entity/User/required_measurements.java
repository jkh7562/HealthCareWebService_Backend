package com.example.iot_project_backserver.Entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class required_measurements {
    @Id
    private String userid;
    private String airflow;
    private String bodytemp;
    private String nibp;
    private String spo2;
    private String ecg;
    private String emg;
    private String gsr;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private com.example.iot_project_backserver.Entity.User.app_user app_user;
}
