package com.eskimi.backend_assignment.service;

import com.eskimi.backend_assignment.exception.InvalidDateException;
import com.eskimi.backend_assignment.model.response.OpenMeteoResponse;
import com.eskimi.backend_assignment.model.response.TemperatureResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class TemperatureStatsService {

    private final WeatherService weatherService;
    private final TemperatureTextConverter temperatureTextConverter;

    public TemperatureStatsService(WeatherService weatherService,
                                   TemperatureTextConverter temperatureTextConverter) {
        this.weatherService = weatherService;
        this.temperatureTextConverter = temperatureTextConverter;
    }

    public TemperatureResponse getTemperatureStats(String startDate, String endDate) {
        validateDates(startDate, endDate);

        OpenMeteoResponse weatherData = weatherService.getTemperatureData(startDate, endDate);

        if (weatherData == null || weatherData.getDaily() == null) {
            throw new RuntimeException("No weather data available");
        }

        OpenMeteoResponse.Daily daily = weatherData.getDaily();

        // Calculate statistics
        Double minTemp = calculateMin(daily.getTemperatureMin());
        Double maxTemp = calculateMax(daily.getTemperatureMax());
        Double avgTemp = calculateAverage(daily.getTemperatureMean());

        // Convert to text
        String minText = temperatureTextConverter.convertTemperatureToText(minTemp);
        String maxText = temperatureTextConverter.convertTemperatureToText(maxTemp);
        String avgText = temperatureTextConverter.convertTemperatureToText(avgTemp);

        return new TemperatureResponse(minTemp, maxTemp, avgTemp, minText, maxText, avgText);
    }

    private void validateDates(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            LocalDate today = LocalDate.now();

            if (start.isAfter(end)) {
                throw new InvalidDateException("Start date must be before or equal to end date");
            }

            if (start.isAfter(today)) {
                throw new InvalidDateException("Start date cannot be in the future");
            }

            // Open-Meteo historical data limitation
            LocalDate minDate = LocalDate.now().minusYears(1);
            if (start.isBefore(minDate)) {
                throw new InvalidDateException(
                        "Start date is too far in the past. Maximum historical data is 1 year");
            }

        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Invalid date format. Use YYYY-MM-DD");
        }
    }

    private Double calculateMin(List<Double> temperatures) {
        if (temperatures == null || temperatures.isEmpty()) {
            return null;
        }
        return round(temperatures.stream()
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(0.0));
    }

    private Double calculateMax(List<Double> temperatures) {
        if (temperatures == null || temperatures.isEmpty()) {
            return null;
        }
        return round(temperatures.stream()
                .mapToDouble(Double::doubleValue)
                .max()
                .orElse(0.0));
    }

    private Double calculateAverage(List<Double> temperatures) {
        if (temperatures == null || temperatures.isEmpty()) {
            return null;
        }
        return round(temperatures.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0));
    }

    private Double round(double value) {
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}

