package com.example.iot_project_backserver.Entity.Volunteer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerAssignmentId implements Serializable {

    private String volunteerid;
    private String userid;
}
