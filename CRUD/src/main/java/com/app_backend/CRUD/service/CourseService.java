package com.app_backend.CRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app_backend.CRUD.model.Course;
import com.app_backend.CRUD.repository.CourseRepository;

@Service
public class CourseService {

    public final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> readCourses() {
        return courseRepository.findAll();
    }

    public Course readCourse(Integer id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course putCourse(Integer id, Course course) {
        Course existingCourse = courseRepository.findById(id).orElse(null);

        existingCourse.setCourseName(course.getCourseName());
        existingCourse.setCourseCode(course.getCourseCode());
        existingCourse.setDepartment(course.getDepartment());
        existingCourse.setCredits(course.getCredits());
        existingCourse.setFacultyName(course.getFacultyName());
        existingCourse.setCourseLevel(course.getCourseLevel());
        existingCourse.setCourseType(course.getCourseType());
        existingCourse.setLanguage(course.getLanguage());
        existingCourse.setMode(course.getMode());
        existingCourse.setExamType(course.getExamType());

        return courseRepository.save(existingCourse);
    }

    public void deleteCourse(Integer id) {
        courseRepository.deleteById(id);
    }
}