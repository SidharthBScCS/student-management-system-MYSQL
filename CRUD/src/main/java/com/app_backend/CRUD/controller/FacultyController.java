package com.app_backend.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app_backend.CRUD.model.Faculty;
import com.app_backend.CRUD.service.FacultyService;

@Controller
@RequestMapping("/faculty")
public class FacultyController {
    public final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public String createFaculty(Faculty faculty) {
        facultyService.saveFaculty(faculty);
        return "redirect:/faculty";
    }

    @GetMapping("/{id}")
    public Faculty readFaculty(@PathVariable Integer id) {
        return facultyService.readFaculty(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteFaculty(@PathVariable Integer id) {
        facultyService.deleteFaculty(id);
        return "redirect:/faculty";
    }

    @PostMapping("/update/{id}")
    public String updateFaculty(@PathVariable Integer id, Faculty faculty) {
        facultyService.updateFaculty(id, faculty);
        return "redirect:/faculty";
    }
    
}
