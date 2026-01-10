package com.eskimi.backend_assignment.unit.service;

import com.eskimi.backend_assignment.service.NumberToWordsService;
import com.eskimi.backend_assignment.service.TemperatureTextConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TemperatureTextConverterTest {

    private TemperatureTextConverter converter;
    private NumberToWordsService numberToWordsService;

    @BeforeEach
    void setUp() {
        numberToWordsService = new NumberToWordsService();
        converter = new TemperatureTextConverter(numberToWordsService);
    }

    @ParameterizedTest
    @CsvSource({
            "-5.4, minus five point four zero",
            "1.3, positive one point three zero",
            "-1.44, minus one point four four",
            "0.0, positive zero",
            "25.5, positive twenty five point five zero",
            "-10.0, minus ten",
            "15.67, positive fifteen point six seven"
    })
    void testConvertTemperatureToText(String temp, String expected) {
        Double temperature = Double.parseDouble(temp);
        String result = converter.convertTemperatureToText(temperature);
        assertEquals(expected, result);
    }

    @Test
    void testNullTemperature() {
        String result = converter.convertTemperatureToText(null);
        assertEquals("", result);
    }

    @Test
    void testRoundingPositive() {
        String result = converter.convertTemperatureToText(25.567);
        assertEquals("positive twenty five point five seven", result);
    }

    @Test
    void testRoundingNegative() {
        String result = converter.convertTemperatureToText(-25.564);
        assertEquals("minus twenty five point five six", result);
    }
}

