package com.example.demo.controller;

import com.example.demo.entity.AirPoint;
import com.example.demo.service.impl.AirPointServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data")
public class AirPointController {
    @Autowired
    private AirPointServiceImpl airPointService;

    @PostMapping("/airPoint/save")
    public String save(@RequestBody AirPoint airPoint){
        airPointService.save(airPoint);
        return "success";
    }

    @DeleteMapping("/airPoint/deleteByPoint/{id}")
    public String deleteByPoint(@PathVariable("id") Long id){
        airPointService.delete(id);
        return  "success";
    }

    @PostMapping("/airPoint/findAll")
    public List<AirPoint> findAll(){
        return airPointService.findAll();
    }

    @PutMapping("/airPoint/update")
    public String update(@RequestBody AirPoint airPoint){
        if(airPointService.update(airPoint)){
            return "success";
        }
        return "fail";
    }
}
