package com.eskimi.backend_assignment.service;

import com.eskimi.backend_assignment.exception.WeatherApiException;
import com.eskimi.backend_assignment.model.response.OpenMeteoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class WeatherService {

    private static final String OPEN_METEO_URL = "https://api.open-meteo.com/v1/forecast";
    private static final double DHAKA_LATITUDE = 23.8103;
    private static final double DHAKA_LONGITUDE = 90.4125;

    private final WebClient webClient;

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(OPEN_METEO_URL)
                .build();
    }

    public OpenMeteoResponse getTemperatureData(String startDate, String endDate) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("latitude", DHAKA_LATITUDE)
                            .queryParam("longitude", DHAKA_LONGITUDE)
                            .queryParam("start_date", startDate)
                            .queryParam("end_date", endDate)
                            .queryParam("daily", "temperature_2m_max,temperature_2m_min,temperature_2m_mean")
                            .queryParam("timezone", "Asia/Dhaka")
                            .build())
                    .retrieve()
                    .bodyToMono(OpenMeteoResponse.class)
                    .timeout(Duration.ofSeconds(10))
                    .block();
        } catch (Exception e) {
            throw new WeatherApiException("Failed to fetch weather data: " + e.getMessage());
        }
    }
}
