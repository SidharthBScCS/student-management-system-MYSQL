package com.app_backend.CRUD.controller;

import com.app_backend.CRUD.service.StudentService;
import com.app_backend.CRUD.service.FacultyService;
import com.app_backend.CRUD.service.CourseService;
import com.app_backend.CRUD.service.DepartmentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    private final StudentService studentService;
    private final FacultyService facultyService;
    private final CourseService courseService;
    private final DepartmentService departmentService;

    public PageController(StudentService studentService,
                          FacultyService facultyService,
                          CourseService courseService,
                          DepartmentService departmentService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
        this.courseService = courseService;
        this.departmentService = departmentService;
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

    @GetMapping("/department")
    public String departmentPage(Model model) {
        model.addAttribute("departments", departmentService.readDepartments());
        return "department";
    }
}