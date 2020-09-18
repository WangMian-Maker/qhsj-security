package com.example.demo.controller;

import com.example.demo.entity.AirData;
import com.example.demo.service.impl.AirDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("data")
public class AirDataController {
    @Autowired
    private AirDataServiceImpl airDataService;

    @GetMapping("/airData/findAllPage/{pageNum}/{pageSize}")
    public Page<AirData> findAllPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return airDataService.find(pageNum,pageSize);

    }

    @GetMapping("/airData/findPageByPoint/{point}/{pageNum}/{pageSize}")
    public Page<AirData> findPageByPoint(@PathVariable("point") String point ,@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return airDataService.findByPoint(point,pageNum,pageSize);
    }


    @PostMapping("/airData/save")
    public String save(@RequestBody AirData airData){
        System.out.println("ojbk"+airData.getAqi());
        airDataService.save(airData);
        return "success";
    }

    @PutMapping("/airData/update")
    public String update(@RequestBody AirData airData){
        airDataService.update(airData);
        return  "success";
    }

    @DeleteMapping("/airData/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id){
        airDataService.deleteById(id);
        return  "success";
    }
}
