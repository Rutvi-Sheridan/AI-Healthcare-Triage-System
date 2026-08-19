package com.healthcare.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.healthcare.backend.dto.TriageRequest;
import com.healthcare.backend.dto.TriageResponse;
import com.healthcare.backend.model.TriageStatus;
import com.healthcare.backend.service.TriageService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/triage-cases")
public class TriageController {

    private final TriageService triageService;

    public TriageController(
            TriageService triageService) {

        this.triageService = triageService;
    }

    @PostMapping
    public ResponseEntity<TriageResponse> createCase(
            @Valid @RequestBody TriageRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(triageService.createCase(request));
    }

    @GetMapping
    public ResponseEntity<List<TriageResponse>>
            getAllCases() {

        return ResponseEntity.ok(
                triageService.getAllCases()
        );
    }

    @GetMapping("/{caseId}")
    public ResponseEntity<TriageResponse> getCase(
            @PathVariable Long caseId) {

        return ResponseEntity.ok(
                triageService.getCaseById(caseId)
        );
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<TriageResponse>>
            getPatientCases(
                    @PathVariable Long patientId) {

        return ResponseEntity.ok(
                triageService.getPatientCases(patientId)
        );
    }

    @PatchMapping("/{caseId}/status")
    public ResponseEntity<TriageResponse> updateStatus(
            @PathVariable Long caseId,
            @RequestParam TriageStatus status) {

        return ResponseEntity.ok(
                triageService.updateStatus(caseId, status)
        );
    }
}