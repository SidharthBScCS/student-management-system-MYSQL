package com.app_backend.CRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app_backend.CRUD.model.Student;
import com.app_backend.CRUD.repository.StudentRepository;

@Service
public class StudentService {
    public final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> readStudents() {
        return studentRepository.findAll();
    }

    public Student readStudent(Integer id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student putStudent(Integer id, Student student) {
        Student existingStudent = studentRepository.findById(id).orElse(null);

        existingStudent.setName(student.getName());
        existingStudent.setCourse(student.getCourse());
        existingStudent.setBatch(student.getBatch());
        existingStudent.setPhone(student.getPhone());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setFatherName(student.getFatherName());
        existingStudent.setMotherName(student.getMotherName());
        existingStudent.setState(student.getState());
        existingStudent.setCountry(student.getCountry());

        return studentRepository.save(existingStudent);
    }
    
    public void deleteStudent(Integer id) {
        studentRepository.deleteById(id);
    }
}
