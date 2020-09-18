package com.example.demo.controller;


import com.example.demo.entity.WaterInfor;
import com.example.demo.service.impl.WaterInforServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data")
public class WaterInforController {
    @Autowired
    private WaterInforServiceImpl waterInforService;

    @GetMapping("/waterInfor/pageFind/{pageNum}/{pageSize}")
    public Page<WaterInfor> findAll(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return  waterInforService.find(pageNum,pageSize);
    }

    @GetMapping("/waterInfor/pageFindByPoint/{point}/{pageNum}/{pageSize}")
    public Page<WaterInfor> findAllByPoint(@PathVariable("point") String point,@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return waterInforService.findByPoint(point,pageNum,pageSize);
    }

    @PostMapping("/waterInfor/save")
    public String save(@RequestBody WaterInfor waterInfor){

        //waterInfor.setWid(waterInforService.maxId());
        //System.out.println(waterInfor.getWid() +"====================================");
        waterInforService.save(waterInfor);
        return "success";
    }

    @PutMapping("/waterInfor/update")
    public  String update(@RequestBody WaterInfor waterInfor){
        waterInforService.save(waterInfor);
        return  "success";
    }

    @DeleteMapping("/waterInfor/deleteById/{id}")
    public void delete(@PathVariable("id") Long id) {
        waterInforService.delete(id);
    }

}
