package com.example.iot_project_backserver.Entity.Medical;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(PatientAssignmentId.class)
public class patient_assignment {
    @Id
    private String medicalid;
    @Id
    private String userid;

    @ManyToOne
    @JoinColumn(name = "medicalid", referencedColumnName = "userid", insertable = false, updatable = false)
    private com.example.iot_project_backserver.Entity.User.app_user medicalUser;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private com.example.iot_project_backserver.Entity.User.app_user app_user;
}
