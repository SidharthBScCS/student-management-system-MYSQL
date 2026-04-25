package com.app_backend.CRUD.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_code")
    private String departmentCode;

    @Column(name = "head_of_department")
    private String headOfDepartment;

    @Column(name = "faculty_count")
    private Integer facultyCount;

    @Column(name = "student_count")
    private Integer studentCount;

    @Column(name = "building_name")
    private String buildingName;

    @Column(name = "floor_number")
    private Integer floorNumber;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "email")
    private String email;

    @Column(name = "department_type")
    private String departmentType;

    public Department() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(String headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    public Integer getFacultyCount() {
        return facultyCount;
    }

    public void setFacultyCount(Integer facultyCount) {
        this.facultyCount = facultyCount;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    @Override
    public String toString() {
        return String.format(
                "Department{id:%d, departmentName:'%s', departmentCode:'%s', headOfDepartment:'%s', facultyCount:%d, studentCount:%d, buildingName:'%s', floorNumber:%d, phone:%d, email:'%s', departmentType:'%s'}",
                id, departmentName, departmentCode, headOfDepartment, facultyCount, studentCount, buildingName, floorNumber, phone, email, departmentType);
    }
}