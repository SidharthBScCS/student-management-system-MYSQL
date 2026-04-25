package com.app_backend.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app_backend.CRUD.model.Student;
import com.app_backend.CRUD.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
    public final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public String createStudent(Student student) {
        studentService.saveStudent(student);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public Student readStudent(@PathVariable Integer id) {
        return studentService.readStudent(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Integer id, Student student) {
        studentService.putStudent(id, student);
        return "redirect:/";
    }

}
