package com.eskimi.backend_assignment.rest.controller;

import com.eskimi.backend_assignment.model.request.DateDifferenceRequest;
import com.eskimi.backend_assignment.model.response.TemperatureResponse;
import com.eskimi.backend_assignment.service.TemperatureStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
@Tag(name = "Weather APIs", description = "Operations related to weather statistics")
public class WeatherController {
    private final Logger LOGGER = LoggerFactory.getLogger(WeatherController.class);

    private final TemperatureStatsService temperatureStatsService;

    public WeatherController(TemperatureStatsService temperatureStatsService) {
        this.temperatureStatsService = temperatureStatsService;
    }

    /**
     * POST /api/v1/weather/dhaka-stats
     * Gives temperature statistics of Dhaka, Bangladesh
     *
     * Request body:
     *  {
     *      "startDate": "2026-01-01",
     *      "endDate": "2026-01-10"
     *  }
     *
     * Response:
     *  {
     *      "min": 10.1,
     *       "max": 23.0,
     *       "average": 15.42,
     *      "minText": "positive ten point one",
     *      "maxText": "positive twenty three",
     *      "averageText": "positive fifteen point four two"
     *  }
     */

    /**
     * curl -X 'POST' \
     * 'http://localhost:8080/api/v1/weather/dhaka-stats' \
     * -H 'Content-Type: application/json' \
     * -d '{
     * "startDate": "2026-01-01",
     * "endDate": "2026-01-09"
     * }'
     */

    @Operation(
            summary = "To find and present the weather statics( temperature) of Dhaka,Bangladesh between two dates",
            description = "Returns min, max and average temperature of Dhaka in both number and text format between startDate and endDate"
    )
    @PostMapping("/dhaka-stats")
    public ResponseEntity<TemperatureResponse> getStats(@Valid @RequestBody DateDifferenceRequest request) {
        LOGGER.info("getStats api hits");
        TemperatureResponse response = temperatureStatsService.getTemperatureStats(
                request.getStartDate(),
                request.getEndDate());
        return ResponseEntity.ok(response);
    }
}
