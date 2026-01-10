package com.eskimi.backend_assignment.rest.controller;

import com.eskimi.backend_assignment.model.request.NumberRequest;
import com.eskimi.backend_assignment.model.response.NumberResponse;
import com.eskimi.backend_assignment.service.NumberToWordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/number")
@Tag(name = "Number APIs", description = "Operations related to number to text conversions")
public class NumberController {
    private final Logger LOGGER = LoggerFactory.getLogger(NumberController.class);

    private final NumberToWordsService numberToWordsService;

    public NumberController(NumberToWordsService numberToWordsService) {
        this.numberToWordsService = numberToWordsService;
    }

    /**
     * POST /api/v1/number/number-to-words
     * Convert number to words
     *
     * Request body:
     * {
     *   "number": 30,
     * }
     *
     * Response:
     * {
     *   "words": "thirty",
     * }
     *
     *
     * curl -X 'POST' \
     *   'http://localhost:8080/api/v1/number/number-to-words' \
     *   -H 'Content-Type:application/json' \
     *   -d '

     * {
     *      "number":999.99
     * }'
     */



    @Operation(
            summary = "To convert numbers to text format",
            description = "Returns the converted text"
    )
    @PostMapping("/number-to-words")
    public ResponseEntity<NumberResponse> convertNumberToWords(@Valid @RequestBody NumberRequest request) {
        LOGGER.info("convertNumberToWords controller hits");
        String words = numberToWordsService.convertToWords(request.getNumber());
        return ResponseEntity.ok(new NumberResponse(words));
    }

}
