package com.example.iot_project_backserver.Entity.Data.Average;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class GsrAverage {
    private Float GsrAverageValue;
    private String userid;
}
