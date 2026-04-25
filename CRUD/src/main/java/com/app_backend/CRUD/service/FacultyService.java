package com.app_backend.CRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app_backend.CRUD.model.Faculty;
import com.app_backend.CRUD.repository.FacultyRepository;

@Service
public class FacultyService {
    public final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty saveFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public List<Faculty> readFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty readFaculty(Integer id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty updateFaculty(Integer id, Faculty faculty) {
        Faculty existingFaculty = facultyRepository.findById(id).orElse(null);

        existingFaculty.setName(faculty.getName());
        existingFaculty.setDepartment(faculty.getDepartment());
        existingFaculty.setDesignation(faculty.getDesignation());
        existingFaculty.setPhone(faculty.getPhone());
        existingFaculty.setEmail(faculty.getEmail());
        existingFaculty.setQualification(faculty.getQualification());
        existingFaculty.setExperience(faculty.getExperience());
        existingFaculty.setState(faculty.getState());
        existingFaculty.setCountry(faculty.getCountry());

        return facultyRepository.save(existingFaculty);
    }

    public void deleteFaculty(Integer id) {
        facultyRepository.deleteById(id);
    }
    
}
