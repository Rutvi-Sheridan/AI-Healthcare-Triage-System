package com.healthcare.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AiTriageRequest(
        @JsonProperty("symptom_text")
        String symptomText,

        int severity,

        String duration,

        Integer age,

        @JsonProperty("follow_up_answers")
        List<FollowUpAnswerDto> followUpAnswers
) {
}