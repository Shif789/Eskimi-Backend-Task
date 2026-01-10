package com.eskimi.backend_assignment.unit.service;

import com.eskimi.backend_assignment.exception.InvalidDateException;
import com.eskimi.backend_assignment.model.response.OpenMeteoResponse;
import com.eskimi.backend_assignment.model.response.TemperatureResponse;
import com.eskimi.backend_assignment.service.NumberToWordsService;
import com.eskimi.backend_assignment.service.TemperatureStatsService;
import com.eskimi.backend_assignment.service.TemperatureTextConverter;
import com.eskimi.backend_assignment.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TemperatureStatsServiceTest {

    @Mock
    private WeatherService weatherService;

    private TemperatureStatsService temperatureStatsService;
    private TemperatureTextConverter temperatureTextConverter;
    private DateTimeFormatter formatter;

    @BeforeEach
    void setUp() {
        NumberToWordsService numberToWordsService = new NumberToWordsService();
        temperatureTextConverter = new TemperatureTextConverter(numberToWordsService);
        temperatureStatsService = new TemperatureStatsService(weatherService, temperatureTextConverter);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    void testGetTemperatureStats() {
        // Use dates within the last year (valid range)
        String startDate = LocalDate.now().minusDays(7).format(formatter);
        String endDate = LocalDate.now().minusDays(4).format(formatter);

        // Mock weather data
        OpenMeteoResponse response = new OpenMeteoResponse();
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.setTemperatureMin(Arrays.asList(20.0, 18.5, 19.0));
        daily.setTemperatureMax(Arrays.asList(30.0, 28.5, 29.0));
        daily.setTemperatureMean(Arrays.asList(25.0, 23.5, 24.0));
        response.setDaily(daily);

        when(weatherService.getTemperatureData(anyString(), anyString())).thenReturn(response);

        TemperatureResponse result = temperatureStatsService.getTemperatureStats(startDate, endDate);

        assertNotNull(result);
        assertEquals(18.5, result.getMin());
        assertEquals(30.0, result.getMax());
        assertEquals(24.17, result.getAverage());
        assertTrue(result.getMinText().contains("positive eighteen"));
        assertTrue(result.getMaxText().contains("positive thirty"));
    }

    @Test
    void testInvalidDateRange_StartAfterEnd() {
        String startDate = LocalDate.now().minusDays(1).format(formatter);
        String endDate = LocalDate.now().minusDays(10).format(formatter);

        assertThrows(InvalidDateException.class, () -> {
            temperatureStatsService.getTemperatureStats(startDate, endDate);
        });
    }

    @Test
    void testInvalidDateRange_FutureDate() {
        String futureDate = LocalDate.now().plusDays(1).format(formatter);

        assertThrows(InvalidDateException.class, () -> {
            temperatureStatsService.getTemperatureStats(futureDate, futureDate);
        });
    }

    @Test
    void testInvalidDateFormat() {
        assertThrows(InvalidDateException.class, () -> {
            temperatureStatsService.getTemperatureStats("01-01-2024", "01-02-2024");
        });
    }

    @Test
    void testNegativeTemperatures() {
        // Use recent dates within valid range
        String startDate = LocalDate.now().minusDays(10).format(formatter);
        String endDate = LocalDate.now().minusDays(7).format(formatter);

        OpenMeteoResponse response = new OpenMeteoResponse();
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.setTemperatureMin(Arrays.asList(-10.0, -15.0, -12.0));
        daily.setTemperatureMax(Arrays.asList(-5.0, -8.0, -6.0));
        daily.setTemperatureMean(Arrays.asList(-7.5, -11.5, -9.0));
        response.setDaily(daily);

        when(weatherService.getTemperatureData(anyString(), anyString())).thenReturn(response);

        TemperatureResponse result = temperatureStatsService.getTemperatureStats(startDate, endDate);

        assertEquals(-15.0, result.getMin());
        assertEquals(-5.0, result.getMax());
        assertTrue(result.getMinText().startsWith("minus"));
        assertTrue(result.getMaxText().startsWith("minus"));
    }

    @Test
    void testDateTooFarInPast() {
        // Test that dates more than 1 year old are rejected
        String oldDate = LocalDate.now().minusYears(1).minusDays(1).format(formatter);
        String recentDate = LocalDate.now().minusDays(1).format(formatter);

        assertThrows(InvalidDateException.class, () -> {
            temperatureStatsService.getTemperatureStats(oldDate, recentDate);
        });
    }

    @Test
    void testValidDateAtBoundary() {
        // Test that dates exactly 1 year old are still valid
        String startDate = LocalDate.now().minusYears(1).plusDays(1).format(formatter);
        String endDate = LocalDate.now().minusDays(1).format(formatter);

        OpenMeteoResponse response = new OpenMeteoResponse();
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.setTemperatureMin(Arrays.asList(15.0));
        daily.setTemperatureMax(Arrays.asList(25.0));
        daily.setTemperatureMean(Arrays.asList(20.0));
        response.setDaily(daily);

        when(weatherService.getTemperatureData(anyString(), anyString())).thenReturn(response);

        TemperatureResponse result = temperatureStatsService.getTemperatureStats(startDate, endDate);

        assertNotNull(result);
        assertEquals(15.0, result.getMin());
        assertEquals(25.0, result.getMax());
        assertEquals(20.0, result.getAverage());
    }

    @Test
    void testSameDayRange() {
        // Test that same start and end date works
        String date = LocalDate.now().minusDays(5).format(formatter);

        OpenMeteoResponse response = new OpenMeteoResponse();
        OpenMeteoResponse.Daily daily = new OpenMeteoResponse.Daily();
        daily.setTemperatureMin(Arrays.asList(22.0));
        daily.setTemperatureMax(Arrays.asList(28.0));
        daily.setTemperatureMean(Arrays.asList(25.0));
        response.setDaily(daily);

        when(weatherService.getTemperatureData(anyString(), anyString())).thenReturn(response);

        TemperatureResponse result = temperatureStatsService.getTemperatureStats(date, date);

        assertNotNull(result);
        assertEquals(22.0, result.getMin());
        assertEquals(28.0, result.getMax());
        assertEquals(25.0, result.getAverage());
    }
}
