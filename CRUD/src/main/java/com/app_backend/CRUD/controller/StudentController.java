package com.app_backend.CRUD.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping
    public List<Student> readAllStudent() {
        return studentService.readStudents();
    }

    @GetMapping("/{id}")
    public Student readStudent(@PathVariable Integer id) {
        return studentService.readStudent(id);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Integer id, Student student) {
        return studentService.putStudent(id, student);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
    }

}
