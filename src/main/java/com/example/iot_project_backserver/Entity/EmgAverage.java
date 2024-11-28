package com.example.iot_project_backserver.Entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class EmgAverage {
    private Float EmgAverageValue;
    private String userid;
}