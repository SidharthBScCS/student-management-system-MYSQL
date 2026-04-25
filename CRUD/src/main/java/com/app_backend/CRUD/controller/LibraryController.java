package com.app_backend.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app_backend.CRUD.model.Library;
import com.app_backend.CRUD.service.LibraryService;

@Controller
@RequestMapping("/library")
public class LibraryController {
    public final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping
    public String createLibrary(Library library) {
        libraryService.saveLibrary(library);
        return "redirect:/library";
    }

    @GetMapping("/{id}")
    public Library readLibrary(@PathVariable Integer id) {
        return libraryService.readLibrary(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteLibrary(@PathVariable Integer id) {
        libraryService.deleteLibrary(id);
        return "redirect:/library";
    }

    @PostMapping("/update/{id}")
    public String updateLibrary(@PathVariable Integer id, Library library) {
        libraryService.putLibrary(id, library);
        return "redirect:/library";
    }

}