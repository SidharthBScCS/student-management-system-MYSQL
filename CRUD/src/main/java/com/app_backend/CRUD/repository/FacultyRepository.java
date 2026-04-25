package com.app_backend.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app_backend.CRUD.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty,Integer>{
    
}
