package com.healthcare.backend.dto;

import jakarta.validation.constraints.NotBlank;

public record FollowUpAnswerDto(
        @NotBlank String question,
        @NotBlank String answer
) {
}