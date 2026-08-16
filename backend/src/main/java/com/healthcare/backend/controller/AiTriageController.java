package com.healthcare.backend.controller;

import com.healthcare.backend.dto.AiTriageResponse;
import com.healthcare.backend.dto.TriageClassificationRequest;
import com.healthcare.backend.service.AiTriageService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/triage")
public class AiTriageController {

    private final AiTriageService aiTriageService;

    public AiTriageController(
            AiTriageService aiTriageService
    ) {
        this.aiTriageService = aiTriageService;
    }

    @PostMapping("/classify")
    public ResponseEntity<AiTriageResponse> classify(
            @Valid
            @RequestBody
            TriageClassificationRequest request
    ) {
        AiTriageResponse response =
                aiTriageService.classify(request);

        return ResponseEntity.ok(response);
    }
}