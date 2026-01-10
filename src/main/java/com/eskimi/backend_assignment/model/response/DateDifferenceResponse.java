package com.eskimi.backend_assignment.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DateDifferenceResponse {
    private String startDate;
    private String endDate;
    private int days;
}
