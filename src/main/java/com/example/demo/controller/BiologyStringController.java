package com.example.demo.controller;

import com.example.demo.entity.biology.BiologyString;
import com.example.demo.repository.BiologyStringRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("data/biologyList")
@Api(tags = "获取生物多样性menu")
public class BiologyStringController {
    @Autowired
    private BiologyStringRepository biologyStringRepository;

    @PostMapping("/findAll")
    public List<BiologyString> findAll(){
        return biologyStringRepository.mFindAll();
    }
}
