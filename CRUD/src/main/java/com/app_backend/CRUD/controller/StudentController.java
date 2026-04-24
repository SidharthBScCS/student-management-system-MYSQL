package com.app_backend.CRUD.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app_backend.CRUD.model.Student;
import com.app_backend.CRUD.service.StudentService;
import org.springframework.ui.Model;

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

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Integer id, Model model) {
        Student student = studentService.readStudent(id);
        model.addAttribute("student", student);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Integer id, Student student) {
        studentService.putStudent(id, student);
        return "redirect:/";
    }

}
