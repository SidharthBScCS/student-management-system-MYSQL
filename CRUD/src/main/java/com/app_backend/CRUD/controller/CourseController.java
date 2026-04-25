package com.app_backend.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app_backend.CRUD.model.Course;
import com.app_backend.CRUD.service.CourseService;

@Controller
@RequestMapping("/course")
public class CourseController {
    public final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public String createCourse(Course course) {
        courseService.saveCourse(course);
        return "redirect:/course";
    }

    @GetMapping("/{id}")
    public Course readCourse(@PathVariable Integer id) {
        return courseService.readCourse(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable Integer id) {
        courseService.deleteCourse(id);
        return "redirect:/course";
    }

    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable Integer id, Course course) {
        courseService.putCourse(id, course);
        return "redirect:/course";
    }

}