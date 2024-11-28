package com.example.iot_project_backserver.Entity.Volunteer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class volunteer {
    @Id
    private String volunteerid;
    private int volunteertime;
}
