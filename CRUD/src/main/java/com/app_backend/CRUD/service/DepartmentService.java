package com.app_backend.CRUD.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app_backend.CRUD.model.Department;
import com.app_backend.CRUD.repository.DepartmentRepository;

@Service
public class DepartmentService {

    public final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public List<Department> readDepartments() {
        return departmentRepository.findAll();
    }

    public Department readDepartment(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    public Department putDepartment(Integer id, Department department) {
        Department existingDepartment = departmentRepository.findById(id).orElse(null);

        existingDepartment.setDepartmentName(department.getDepartmentName());
        existingDepartment.setDepartmentCode(department.getDepartmentCode());
        existingDepartment.setHeadOfDepartment(department.getHeadOfDepartment());
        existingDepartment.setFacultyCount(department.getFacultyCount());
        existingDepartment.setStudentCount(department.getStudentCount());
        existingDepartment.setBuildingName(department.getBuildingName());
        existingDepartment.setFloorNumber(department.getFloorNumber());
        existingDepartment.setPhone(department.getPhone());
        existingDepartment.setEmail(department.getEmail());
        existingDepartment.setDepartmentType(department.getDepartmentType());

        return departmentRepository.save(existingDepartment);
    }

    public void deleteDepartment(Integer id) {
        departmentRepository.deleteById(id);
    }
}