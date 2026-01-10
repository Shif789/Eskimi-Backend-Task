package com.eskimi.backend_assignment.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TemperatureTextConverter {

    private final NumberToWordsService numberToWordsService;

    public TemperatureTextConverter(NumberToWordsService numberToWordsService) {
        this.numberToWordsService = numberToWordsService;
    }

    public String convertTemperatureToText(Double temperature) {
        if (temperature == null) {
            return "";
        }

        // Round to 2 decimal places
        BigDecimal temp = BigDecimal.valueOf(temperature)
                .setScale(2, RoundingMode.HALF_UP);

        String prefix;
        BigDecimal absTemp;

        if (temp.compareTo(BigDecimal.ZERO) < 0) {
            prefix = "minus";
            absTemp = temp.abs();
        } else {
            prefix = "positive";
            absTemp = temp;
        }

        String numberWords = numberToWordsService.convertToWords(absTemp);

        return prefix + " " + numberWords;
    }
}
