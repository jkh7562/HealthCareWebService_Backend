package com.example.iot_project_backserver.Entity.Volunteer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(DesiredVolunteerDateId.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class desired_volunteer_date {

    @Id
    private String userid;
    @Id
    private String desireddate;
    private String text;

    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false)
    private com.example.iot_project_backserver.Entity.User.app_user app_user;
}
