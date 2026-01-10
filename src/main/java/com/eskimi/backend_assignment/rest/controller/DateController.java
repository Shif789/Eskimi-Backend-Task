package com.eskimi.backend_assignment.rest.controller;

import com.eskimi.backend_assignment.model.request.DateDifferenceRequest;
import com.eskimi.backend_assignment.model.response.DateDifferenceResponse;
import com.eskimi.backend_assignment.service.DaysCalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dates")
@Tag(name = "Date APIs", description = "Operations related to date calculations")
public class DateController {
    private final Logger LOGGER = LoggerFactory.getLogger(DateController.class);

    private final DaysCalculatorService daysCalculatorService;

    public DateController(DaysCalculatorService daysCalculatorService) {
        this.daysCalculatorService = daysCalculatorService;
    }

    /**
     * POST /api/v1/dates/difference
     * Calculate number of days between two dates
     * <p>
     * Request body:
     * {
     * "startDate": "2024-01-01",
     * "endDate": "2024-12-31"
     * }
     * <p>
     * Response:
     * {
     * "startDate": "2024-01-01",
     * "endDate": "2024-12-31",
     * "days": 365
     * }
     * <p>
     * below is the curl command you can run on your terminal if you are running on the localhost
     * if you are running anywhere else then replace localhost with the url of that server
     *
     * curl -X POST http://localhost:8080/api/v1/dates/difference \
     * -H "Content-Type: application/json" \
     * -d '{"startDate":"2024-01-01","endDate":"2024-12-31"}'
     */

    @Operation(
            summary = "Calculate number of days between two dates",
            description = "Returns the number of days between startDate and endDate"
    )
    @PostMapping("/difference")
    public ResponseEntity<DateDifferenceResponse> getDaysBetween(@Valid @RequestBody DateDifferenceRequest request) {
        LOGGER.info("getDaysBetween api hits");
        int days = daysCalculatorService.calculateDays(
                request.getStartDate(),
                request.getEndDate()
        );

        DateDifferenceResponse dateDifferenceResponse = DateDifferenceResponse
                .builder()
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .days(days)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(dateDifferenceResponse);
    }

    /**
     * GET /api/health
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Days Calculator API");
        return ResponseEntity.ok(response);
    }
}
