package com.example.demo.controller;

import com.example.demo.entity.ProtectGrade;
import com.example.demo.repository.ProtectGradeRepository;
import org.aspectj.weaver.patterns.PerObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("data/protectGrade")
@RestController
public class ProtectGradeController {
    @Autowired
    private ProtectGradeRepository protectGradeRepository;

    @PostMapping("/findAll")
    public List<ProtectGrade> findAll(){
        return protectGradeRepository.findAll();
    }
}
