package com.eskimi.backend_assignment.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenMeteoResponse {
    @JsonProperty("daily")
    private Daily daily;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder    public static class Daily {
        @JsonProperty("temperature_2m_max")
        private List<Double> temperatureMax;

        @JsonProperty("temperature_2m_min")
        private List<Double> temperatureMin;

        @JsonProperty("temperature_2m_mean")
        private List<Double> temperatureMean;

    }
}
