package com.example.iot_project_backserver.Entity.Medical;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientAssignmentId implements Serializable {
    private String medicalid;
    private String userid;
}
