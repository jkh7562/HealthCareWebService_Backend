package com.example.iot_project_backserver.Entity.Volunteer;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DesiredVolunteerDateId implements Serializable {
    private String userid;
    private String desireddate;
}
