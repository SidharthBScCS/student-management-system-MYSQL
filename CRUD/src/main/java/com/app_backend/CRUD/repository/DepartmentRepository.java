package com.app_backend.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app_backend.CRUD.model.Department;

public interface DepartmentRepository extends JpaRepository<Department,Integer>{
    
}
