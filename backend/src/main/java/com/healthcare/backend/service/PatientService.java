package com.healthcare.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.backend.model.Patient;
import com.healthcare.backend.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    // Constructor injection
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Save a new patient
    public Patient savePatient(Patient patient) {

        if (patient == null) {
            throw new IllegalArgumentException(
                    "Patient information cannot be null."
            );
        }

        if (patient.getUser() == null) {
            throw new IllegalArgumentException(
                    "Patient must be connected to a user account."
            );
        }

        return patientRepository.save(patient);
    }

    // Get all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Get one patient by ID
    public Patient getPatientById(Long patientId) {

        return patientRepository.findById(patientId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Patient not found with ID: " + patientId
                        )
                );
    }

    // Update patient information
    public Patient updatePatient(
            Long patientId,
            Patient updatedPatient) {

        if (updatedPatient == null) {
            throw new IllegalArgumentException(
                    "Updated patient information cannot be null."
            );
        }

        Patient existingPatient =
                getPatientById(patientId);

        existingPatient.setDateOfBirth(
                updatedPatient.getDateOfBirth()
        );

        existingPatient.setPhoneNumber(
                updatedPatient.getPhoneNumber()
        );

        existingPatient.setAddress(
                updatedPatient.getAddress()
        );

        return patientRepository.save(existingPatient);
    }

    // Delete patient
    public void deletePatient(Long patientId) {

        if (!patientRepository.existsById(patientId)) {
            throw new IllegalArgumentException(
                    "Patient not found with ID: " + patientId
            );
        }

        patientRepository.deleteById(patientId);
    }
}