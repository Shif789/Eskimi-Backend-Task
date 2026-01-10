package com.eskimi.backend_assignment.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class NumberToWordsService {

    private static final String[] ONES = {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    private static final String[] ONES_FOR_INTEGER = {
            "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    private static final String[] TENS = {
            "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    private static final String[] TEENS = {
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen",
            "sixteen", "seventeen", "eighteen", "nineteen"
    };

    public String convertToWords(BigDecimal number) {
        // Round to 2 decimal places to handle floating-point precision
        number = number.setScale(2, RoundingMode.HALF_UP);

        // Split into integer and decimal parts
        BigDecimal integerPart = number.setScale(0, RoundingMode.DOWN);
        BigDecimal decimalPart = number.subtract(integerPart);

        StringBuilder result = new StringBuilder();

        // Convert integer part
        int intValue = integerPart.intValue();
        if (intValue == 0) {
            result.append("zero");
        } else {
            result.append(convertIntegerToWords(intValue));
        }

        // Convert decimal part if exists
        if (decimalPart.compareTo(BigDecimal.ZERO) > 0) {
            result.append(" point");

            // Format decimal part to always have exactly 2 digits
            String decimalStr = String.format("%.2f", decimalPart.doubleValue()).substring(2);

            for (char digit : decimalStr.toCharArray()) {
                int digitValue = Character.getNumericValue(digit);
                // Use ONES array which includes "zero" at index 0
                result.append(" ").append(ONES[digitValue]);
            }
        }

        return result.toString().trim();
    }

    private String convertIntegerToWords(int number) {
        if (number == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        // Handle hundreds
        int hundreds = number / 100;
        if (hundreds > 0) {
            result.append(ONES_FOR_INTEGER[hundreds]).append(" hundred");
            number %= 100;
            if (number > 0) {
                result.append(" ");
            }
        }

        // Handle tens and ones
        if (number >= 10 && number < 20) {
            result.append(TEENS[number - 10]);
        } else {
            int tens = number / 10;
            int ones = number % 10;

            if (tens > 0) {
                result.append(TENS[tens]);
                if (ones > 0) {
                    result.append(" ");
                }
            }

            if (ones > 0) {
                result.append(ONES_FOR_INTEGER[ones]);
            }
        }

        return result.toString();
    }
}
