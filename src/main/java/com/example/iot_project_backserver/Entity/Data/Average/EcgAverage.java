package com.example.iot_project_backserver.Entity.Data.Average;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class EcgAverage {

    private Float EcgAverageValue;
    private String userid;

}
