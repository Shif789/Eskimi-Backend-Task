package com.eskimi.backend_assignment.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TemperatureResponse {
    private double min;
    private double max;
    private double average;
    private String minText;
    private String maxText;
    private String averageText;
}
