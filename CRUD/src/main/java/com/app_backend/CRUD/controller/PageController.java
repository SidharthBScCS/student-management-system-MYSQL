package com.app_backend.CRUD.controller;

import com.app_backend.CRUD.service.StudentService;
import com.app_backend.CRUD.service.FacultyService;
import com.app_backend.CRUD.service.CourseService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final StudentService studentService;
    private final FacultyService facultyService;
    private final CourseService courseService;

    public PageController(StudentService studentService,
                          FacultyService facultyService,
                          CourseService courseService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
        this.courseService = courseService;
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

    @GetMapping("/course")
    public String coursePage(Model model) {
        model.addAttribute("courses", courseService.readCourses());
        return "course";
    }
}