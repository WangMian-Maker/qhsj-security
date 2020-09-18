package com.example.demo.controller;


import com.example.demo.entity.WaterPoint;
import com.example.demo.repository.WaterPointRepository;
import com.example.demo.service.impl.WaterInforServiceImpl;
import com.example.demo.service.impl.WaterPointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data")
public class WaterPointController {
    @Autowired
    private WaterPointServiceImpl waterPointService;

    @PostMapping("/waterCheckPoint/save")
    public String save(@RequestBody WaterPoint waterPoint){
        waterPointService.save(waterPoint);
        return "success";
    }

    @DeleteMapping("/waterCheckPoint/deleteById/{id}")
    public String deleteByPoint(@PathVariable("id") Long id){
        waterPointService.delete(id);
        return  "success";
    }

    @PutMapping("/waterCheckPoint/update")
    public String update(@RequestBody WaterPoint waterPoint){
        if(waterPointService.update(waterPoint)){
            return "success";
        }
        return "fail";
    }

    @PostMapping("/waterCheckPoint/findAll")
    public List<WaterPoint> findAll(){
        return waterPointService.findAll();
    }
}
