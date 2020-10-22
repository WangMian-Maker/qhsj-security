package com.example.demo.controller;

import com.example.demo.entity.Department;
import com.example.demo.service.impl.DepartmentServiceImpl;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data/department")
public class DepartmentController {
    @Autowired
    private DepartmentServiceImpl departmentService;

    @PostMapping("/findByLikeDepartmentName")
    public List<Department> findByLikeDepartmentName(@RequestBody String departmentName){
        JSONObject object=JSONObject.fromObject(departmentName);
        String name=object.getString("departmentName");
        return departmentService.findByLikeDepartmentName(name);
    }

    @PostMapping("/findByDepartmentName/{name}")
    public List<Department> findByDepartmentName(@PathVariable("name") String name){
        return departmentService.findByLikeDepartmentName(name);
    }
}
