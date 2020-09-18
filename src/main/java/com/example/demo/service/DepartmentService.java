package com.example.demo.service;

import com.example.demo.entity.Department;

import java.util.List;

public interface DepartmentService {
    public Department findByDid(Long did);
    public List<Department> findByLikeDepartmentName(String departmentName);
}
