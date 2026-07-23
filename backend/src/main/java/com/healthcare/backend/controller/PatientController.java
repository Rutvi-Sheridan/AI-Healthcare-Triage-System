package com.healthcare.backend.controller;

import com.healthcare.backend.model.Patient;
import com.healthcare.backend.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {

    private final PatientRepository patientRepository;

    public PatientController(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // GET all patients
    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientRepository.findAll());
    }

    // GET one patient
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        return patientRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE patient
    @PostMapping
    public ResponseEntity<Patient> createPatient(
            @RequestBody Patient patient) {

        Patient savedPatient = patientRepository.save(patient);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPatient);
    }

    // UPDATE patient
    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable Long id,
            @RequestBody Patient updatedPatient) {

        return patientRepository.findById(id)
                .map(existingPatient -> {
                    existingPatient.setAddress(updatedPatient.getAddress());
                    existingPatient.setDateOfBirth(
                            updatedPatient.getDateOfBirth());
                    existingPatient.setPhoneNumber(
                            updatedPatient.getPhoneNumber());
                    existingPatient.setUser(updatedPatient.getUser());

                    Patient savedPatient =
                            patientRepository.save(existingPatient);

                    return ResponseEntity.ok(savedPatient);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE patient
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {

        if (!patientRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        patientRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}