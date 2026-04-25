package com.app_backend.CRUD.controller;

import com.app_backend.CRUD.service.StudentService;
import com.app_backend.CRUD.service.FacultyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final StudentService studentService;
    private final FacultyService facultyService;

    public PageController(StudentService studentService,
                          FacultyService facultyService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("students", studentService.readStudents());
        return "index";
    }

    @GetMapping("/faculty")
    public String facultyPage(Model model) {
        model.addAttribute("faculties", facultyService.readFaculties());
        return "faculty";
    }
}