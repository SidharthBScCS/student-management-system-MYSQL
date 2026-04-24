package com.app_backend.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app_backend.CRUD.model.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{
    
}
