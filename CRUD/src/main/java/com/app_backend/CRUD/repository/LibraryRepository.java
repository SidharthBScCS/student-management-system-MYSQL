package com.app_backend.CRUD.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app_backend.CRUD.model.Library;

public interface LibraryRepository extends JpaRepository<Library,Integer>{
    
}
