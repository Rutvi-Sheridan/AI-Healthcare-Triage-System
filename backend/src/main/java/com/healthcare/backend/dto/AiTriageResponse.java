package com.healthcare.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AiTriageResponse(
        @JsonProperty("urgency_level")
        String urgencyLevel,

        String recommendation,

        String explanation,

        String summary,

        @JsonProperty("red_flag_found")
        boolean redFlagFound,

        @JsonProperty("matched_red_flags")
        List<String> matchedRedFlags,

        @JsonProperty("extracted_symptoms")
        List<String> extractedSymptoms,

        double confidence,

        @JsonProperty("classifier_source")
        String classifierSource,

        @JsonProperty("requires_staff_review")
        boolean requiresStaffReview,

        String disclaimer
) {
}