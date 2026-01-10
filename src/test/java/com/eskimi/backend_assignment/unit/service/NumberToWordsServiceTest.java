package com.eskimi.backend_assignment.unit.service;

import com.eskimi.backend_assignment.service.NumberToWordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NumberToWordsServiceTest {

    private NumberToWordsService service;

    @BeforeEach
    void setUp() {
        service = new NumberToWordsService();
    }

    @ParameterizedTest
    @CsvSource({
            "36, thirty six",
            "105, one hundred five",
            "36.40, thirty six point four zero",
            "0, zero",
            "1, one",
            "10, ten",
            "11, eleven",
            "15, fifteen",
            "19, nineteen",
            "20, twenty",
            "21, twenty one",
            "99, ninety nine",
            "100, one hundred",
            "101, one hundred one",
            "200, two hundred",
            "999, nine hundred ninety nine",
            "0.01, zero point zero one",
            "0.99, zero point nine nine",
            "1.50, one point five zero",
            "999.99, nine hundred ninety nine point nine nine"
    })
    void testConvertToWords(String input, String expected) {
        BigDecimal number = new BigDecimal(input);
        String result = service.convertToWords(number);
        assertEquals(expected, result);
    }

    @Test
    void testFloatingPointPrecision() {
        BigDecimal number = new BigDecimal("36.4");
        String result = service.convertToWords(number);
        assertEquals("thirty six point four zero", result);
    }

    @Test
    void testRoundingEdgeCase() {
        BigDecimal number = new BigDecimal("36.404");
        String result = service.convertToWords(number);
        assertEquals("thirty six point four zero", result);
    }

    @Test
    void testRoundingUp() {
        BigDecimal number = new BigDecimal("36.406");
        String result = service.convertToWords(number);
        assertEquals("thirty six point four one", result);
    }

    @Test
    void testMultipleTeens() {
        assertEquals("thirteen", service.convertToWords(new BigDecimal("13")));
        assertEquals("fourteen", service.convertToWords(new BigDecimal("14")));
        assertEquals("eighteen", service.convertToWords(new BigDecimal("18")));
    }

    @Test
    void testHundredsWithoutTens() {
        assertEquals("one hundred one", service.convertToWords(new BigDecimal("101")));
        assertEquals("five hundred five", service.convertToWords(new BigDecimal("505")));
    }
}
