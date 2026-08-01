package com.healthcare.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TriageClassificationRequest(
        @NotBlank
        @Size(min = 3, max = 5000)
        String symptomText,

        @Min(1)
        @Max(5)
        int severity,

        @Size(max = 100)
        String duration,

        @Min(0)
        @Max(120)
        Integer age,

        List<@Valid FollowUpAnswerDto> followUpAnswers
) {
}