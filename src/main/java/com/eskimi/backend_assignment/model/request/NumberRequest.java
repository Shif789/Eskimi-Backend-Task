package com.eskimi.backend_assignment.model.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberRequest {
    @NotNull(message = "Number is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Number must be >= 0")
    @DecimalMax(value = "999.99", inclusive = true, message = "Number must be < 1000")
    private BigDecimal number;
}
