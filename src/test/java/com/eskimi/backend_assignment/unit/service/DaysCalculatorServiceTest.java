package com.eskimi.backend_assignment.unit.service;

import com.eskimi.backend_assignment.exception.InvalidDateException;
import com.eskimi.backend_assignment.service.DaysCalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class DaysCalculatorServiceTest {
    private final DaysCalculatorService service = new DaysCalculatorService();

    @Test
    @DisplayName("Should return 0 days for same date")
    void testSameDate() {
        assertEquals(0, service.calculateDays("2024-01-01", "2024-01-01"));
    }

    @Test
    @DisplayName("Should calculate days within same month")
    void testSameMonth() {
        assertEquals(10, service.calculateDays("2024-01-01", "2024-01-11"));
        assertEquals(30, service.calculateDays("2024-01-01", "2024-01-31"));
    }

    @Test
    @DisplayName("Should calculate days across months")
    void testAcrossMonths() {
        assertEquals(31, service.calculateDays("2024-01-01", "2024-02-01"));
        // Jan 1 to Mar 1: Jan (31 days) + Feb (29 days in 2024 leap year) = 60 days
        assertEquals(60, service.calculateDays("2024-01-01", "2024-03-01"));
    }

    @Test
    @DisplayName("Should calculate days across years")
    void testAcrossYears() {
        // 2023 is not a leap year: 365 days
        assertEquals(365, service.calculateDays("2023-01-01", "2024-01-01"));
        // 2024 is a leap year: 366 days
        assertEquals(366, service.calculateDays("2024-01-01", "2025-01-01"));
    }

    @Test
    @DisplayName("Should handle leap years correctly")
    void testLeapYear() {
        // 2024 is a leap year (Feb has 29 days)
        assertEquals(29, service.calculateDays("2024-02-01", "2024-03-01"));

        // 2023 is not a leap year (Feb has 28 days)
        assertEquals(28, service.calculateDays("2023-02-01", "2023-03-01"));
    }

    @Test
    @DisplayName("Should return absolute value (order doesn't matter)")
    void testReverseOrder() {
        assertEquals(10, service.calculateDays("2024-01-11", "2024-01-01"));
        assertEquals(365, service.calculateDays("2024-01-01", "2023-01-01"));
    }

    @Test
    @DisplayName("Should throw exception for invalid date format")
    void testInvalidFormat() {
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("01-01-2024", "2024-01-11"));
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("2024/01/01", "2024-01-11"));
    }

    @Test
    @DisplayName("Should throw exception for invalid month")
    void testInvalidMonth() {
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("2024-00-01", "2024-01-11"));
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("2024-13-01", "2024-01-11"));
    }

    @Test
    @DisplayName("Should throw exception for invalid day")
    void testInvalidDay() {
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("2024-01-00", "2024-01-11"));
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("2024-01-32", "2024-01-11"));
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("2024-02-30", "2024-03-01"));
    }

    @Test
    @DisplayName("Should handle century leap year rules")
    void testCenturyLeapYear() {
        // 2000 is a leap year (divisible by 400)
        assertEquals(366, service.calculateDays("2000-01-01", "2001-01-01"));

        // 1900 was not a leap year (divisible by 100 but not 400)
        assertEquals(365, service.calculateDays("1900-01-01", "1901-01-01"));
    }

    @Test
    @DisplayName("Should calculate large date differences")
    void testLargeDifference() {
        // From 2000-01-01 to 2010-01-01
        // Leap years: 2000, 2004, 2008 = 3 leap years
        // Total: (365 × 10) + 3 = 3653 days
        assertEquals(3653, service.calculateDays("2000-01-01", "2010-01-01"));

        // From 1900-01-01 to 2000-01-01 (100 years)
        // Leap years in this period: 24 leap years
        // (2000 is leap, 1900 is not, every 4 years except century years not divisible by 400)
        // 1904, 1908, ..., 1996, 2000 = 24 leap years
        // Total: (365 × 100) + 24 = 36524 days
        assertEquals(36524, service.calculateDays("1900-01-01", "2000-01-01"));
    }

    @Test
    @DisplayName("Should handle various date scenarios")
    void testVariousScenarios() {
        // One week
        assertEquals(7, service.calculateDays("2024-01-01", "2024-01-08"));

        // One month (30 days)
        assertEquals(30, service.calculateDays("2024-04-01", "2024-05-01"));

        // One month (31 days)
        assertEquals(31, service.calculateDays("2024-05-01", "2024-06-01"));

        // Across leap day
        assertEquals(1, service.calculateDays("2024-02-28", "2024-02-29"));
        assertEquals(2, service.calculateDays("2024-02-28", "2024-03-01"));
    }

    @Test
    @DisplayName("Should throw exception for null or empty dates")
    void testNullOrEmptyDates() {
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays(null, "2024-01-01"));
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("2024-01-01", null));
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("", "2024-01-01"));
    }

    @Test
    @DisplayName("Should validate year range")
    void testYearRange() {
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("0000-01-01", "2024-01-01"));
        assertThrows(InvalidDateException.class,
                () -> service.calculateDays("10000-01-01", "2024-01-01"));
    }
}
