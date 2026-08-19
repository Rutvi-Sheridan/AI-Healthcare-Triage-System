package com.healthcare.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.healthcare.backend.model.Doctor;
import com.healthcare.backend.repository.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    // Constructor
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Save doctor
    public Doctor saveDoctor(Doctor doctor) {

        if (doctor == null) {
            throw new IllegalArgumentException(
                    "Doctor information cannot be null.");
        }

        if (doctor.getUser() == null) {
            throw new IllegalArgumentException(
                    "Doctor must be connected to a user account.");
        }

        return doctorRepository.save(doctor);
    }

    // Get all doctors
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    // Get doctor by ID
    public Doctor getDoctorById(Long doctorId) {

        return doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Doctor not found with ID: " + doctorId));
    }

    // Update doctor
    public Doctor updateDoctor(Long doctorId,
                               Doctor updatedDoctor) {

        Doctor existingDoctor =
                getDoctorById(doctorId);

        existingDoctor.setSpecialization(
                updatedDoctor.getSpecialization());

        existingDoctor.setAvailabilityStatus(
                updatedDoctor.getAvailabilityStatus());

        return doctorRepository.save(existingDoctor);
    }

    // Delete doctor
    public void deleteDoctor(Long doctorId) {

        if (!doctorRepository.existsById(doctorId)) {
            throw new IllegalArgumentException(
                    "Doctor not found with ID: " + doctorId);
        }

        doctorRepository.deleteById(doctorId);
    }

}