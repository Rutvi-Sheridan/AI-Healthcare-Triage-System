package com.healthcare.backend.controller;

import com.healthcare.backend.model.Doctor;
import com.healthcare.backend.repository.DoctorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // GET all doctors
    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorRepository.findAll());
    }

    // GET one doctor
    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable Long id) {
        return doctorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // CREATE doctor
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(
            @RequestBody Doctor doctor) {

        Doctor savedDoctor = doctorRepository.save(doctor);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedDoctor);
    }

    // UPDATE doctor
    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(
            @PathVariable Long id,
            @RequestBody Doctor updatedDoctor) {

        return doctorRepository.findById(id)
                .map(existingDoctor -> {
                    existingDoctor.setSpecialization(
                            updatedDoctor.getSpecialization());

                    existingDoctor.setAvailabilityStatus(
                            updatedDoctor.getAvailabilityStatus());

                    existingDoctor.setUser(updatedDoctor.getUser());

                    Doctor savedDoctor =
                            doctorRepository.save(existingDoctor);

                    return ResponseEntity.ok(savedDoctor);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE doctor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {

        if (!doctorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        doctorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}