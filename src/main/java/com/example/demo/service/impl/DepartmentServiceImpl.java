package com.example.demo.service.impl;


import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public Department findByDid(Long did) {
        return departmentRepository.findBydid(did);
    }

    @Override
    public List<Department> findByLikeDepartmentName(String departmentName) {
        departmentName="%"+departmentName+"%";
        return departmentRepository.findByLikeDepartment(departmentName);
    }
}
