package com.eskimi.backend_assignment.service;

import com.eskimi.backend_assignment.exception.InvalidDateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DaysCalculatorService {
    private final Logger LOGGER = LoggerFactory.getLogger(DaysCalculatorService.class);

    /**
     * Calculate the number of days between two dates
     * @param startDateStr Start date in YYYY-MM-DD format
     * @param endDateStr End date in YYYY-MM-DD format
     * @return Absolute number of days between the dates
     */
    public int calculateDays(String startDateStr, String endDateStr) {
        int[] startDate = parseDate(startDateStr);
        int[] endDate = parseDate(endDateStr);

        validateDate(startDate);
        validateDate(endDate);

        int startDays = convertDateToDays(startDate);
        int endDays = convertDateToDays(endDate);

        return Math.abs(endDays - startDays);
    }

    /**
     * Parse date string in YYYY-MM-DD format
     */
    private int[] parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            throw new InvalidDateException("Date cannot be null or empty");
        }

        String[] parts = dateStr.split("-");
        if (parts.length != 3) {
            throw new InvalidDateException("Invalid date format. Expected YYYY-MM-DD, got: " + dateStr);
        }

        try {
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            return new int[]{year, month, day};
        } catch (NumberFormatException e) {
            throw new InvalidDateException("Invalid date values in: " + dateStr);
        }
    }

    /**
     * Validate if the date is valid
     */
    private void validateDate(int[] date) {
        int year = date[0];
        int month = date[1];
        int day = date[2];

        if (year < 1 || year > 9999) {
            throw new InvalidDateException("Year must be between 1 and 9999, got: " + year);
        }

        if (month < 1 || month > 12) {
            throw new InvalidDateException("Month must be between 1 and 12, got: " + month);
        }

        int maxDay = getDaysInMonth(year, month);
        if (day < 1 || day > maxDay) {
            throw new InvalidDateException(
                    String.format("Day must be between 1 and %d for month %d, got: %d", maxDay, month, day)
            );
        }
    }

    /**
     * Convert a date to the total number of days since year 1
     */
    private int convertDateToDays(int[] date) {
        int year = date[0];
        int month = date[1];
        int day = date[2];

        int totalDays = 0;

        // Add days for all complete years before the given year
        for (int y = 1; y < year; y++) {
            totalDays += isLeapYear(y) ? 366 : 365;
        }

        // Add days for all complete months in the current year
        for (int m = 1; m < month; m++) {
            totalDays += getDaysInMonth(year, m);
        }

        // Add the remaining days in the current month
        totalDays += day;

        return totalDays;
    }

    /**
     * Check if a year is a leap year
     * Leap year rules:
     * - Divisible by 4
     * - Exception: divisible by 100 (not a leap year)
     * - Exception to exception: divisible by 400 (is a leap year)
     */
    private boolean isLeapYear(int year) {
        if (year % 400 == 0) {
            return true;
        }
        if (year % 100 == 0) {
            return false;
        }
        return year % 4 == 0;
    }

    /**
     * Get the number of days in a specific month
     */
    private int getDaysInMonth(int year, int month) {
        switch (month) {
            case 1:  // January
            case 3:  // March
            case 5:  // May
            case 7:  // July
            case 8:  // August
            case 10: // October
            case 12: // December
                return 31;
            case 4:  // April
            case 6:  // June
            case 9:  // September
            case 11: // November
                return 30;
            case 2:  // February
                return isLeapYear(year) ? 29 : 28;
            default:
                throw new InvalidDateException("Invalid month: " + month);
        }
    }
}
