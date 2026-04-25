package com.app_backend.CRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app_backend.CRUD.model.Library;
import com.app_backend.CRUD.repository.LibraryRepository;

@Service
public class LibraryService {

    public final LibraryRepository libraryRepository;

    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    public Library saveLibrary(Library library) {
        return libraryRepository.save(library);
    }

    public List<Library> readLibraries() {
        return libraryRepository.findAll();
    }

    public Library readLibrary(Integer id) {
        return libraryRepository.findById(id).orElse(null);
    }

    public Library putLibrary(Integer id, Library library) {
        Library existingLibrary = libraryRepository.findById(id).orElse(null);

        existingLibrary.setBookName(library.getBookName());
        existingLibrary.setBookCode(library.getBookCode());
        existingLibrary.setAuthorName(library.getAuthorName());
        existingLibrary.setPublisher(library.getPublisher());
        existingLibrary.setCategory(library.getCategory());
        existingLibrary.setLanguage(library.getLanguage());
        existingLibrary.setQuantity(library.getQuantity());
        existingLibrary.setRackNumber(library.getRackNumber());
        existingLibrary.setFloorNumber(library.getFloorNumber());
        existingLibrary.setAvailabilityStatus(library.getAvailabilityStatus());

        return libraryRepository.save(existingLibrary);
    }

    public void deleteLibrary(Integer id) {
        libraryRepository.deleteById(id);
    }
}