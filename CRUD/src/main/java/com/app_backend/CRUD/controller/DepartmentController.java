package com.app_backend.CRUD.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app_backend.CRUD.model.Department;
import com.app_backend.CRUD.service.DepartmentService;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    public final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public String createDepartment(Department department) {
        departmentService.saveDepartment(department);
        return "redirect:/department";
    }

    @GetMapping("/{id}")
    public Department readDepartment(@PathVariable Integer id) {
        return departmentService.readDepartment(id);
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable Integer id) {
        departmentService.deleteDepartment(id);
        return "redirect:/department";
    }

    @PostMapping("/update/{id}")
    public String updateDepartment(@PathVariable Integer id, Department department) {
        departmentService.putDepartment(id, department);
        return "redirect:/department";
    }

}