package com.healthcare.backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.healthcare.backend.dto.TriageRequest;
import com.healthcare.backend.dto.TriageResponse;
import com.healthcare.backend.model.Patient;
import com.healthcare.backend.model.TriageCase;
import com.healthcare.backend.model.UrgencyLevel;
import com.healthcare.backend.model.User;
import com.healthcare.backend.repository.PatientRepository;
import com.healthcare.backend.repository.TriageCaseRepository;

@ExtendWith(MockitoExtension.class)
class TriageServiceTest {

    @Mock
    private TriageCaseRepository triageCaseRepository;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private TriageService triageService;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setName("Test Patient");

        Patient patient = new Patient();
        patient.setPatientId(1L);
        patient.setUser(user);

        when(patientRepository.findById(1L))
                .thenReturn(Optional.of(patient));

        when(triageCaseRepository.save(any(TriageCase.class)))
                .thenAnswer(invocation -> {
                    TriageCase triageCase = invocation.getArgument(0);
                    triageCase.setCaseId(1L);
                    triageCase.setCaseDate(LocalDateTime.now());
                    return triageCase;
                });
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "I have pain in heart",
            "I have pain in my heart",
            "I have heart pain",
            "I feel pressure in chest",
            "I have chest tightness"
    })
    void heartAndChestWordingReturnsEmergency(String complaint) {
        TriageResponse response = createCase(complaint);

        assertEquals(
                UrgencyLevel.EMERGENCY,
                response.getUrgencyLevel()
        );
        assertTrue(response.isRedFlagFound());
    }

    @Test
    void heartburnDoesNotMatchHeartPainRule() {
        TriageResponse response = createCase(
                "I have heartburn after eating spicy food"
        );

        assertEquals(
                UrgencyLevel.LOW,
                response.getUrgencyLevel()
        );
        assertFalse(response.isRedFlagFound());
    }

    private TriageResponse createCase(String complaint) {
        TriageRequest request = new TriageRequest();
        request.setPatientId(1L);
        request.setChiefComplaint(complaint);

        return triageService.createCase(request);
    }
}