package com.example.demo.controller;

import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.BirdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data/bird")
public class BirdController {
    @Autowired
    private BirdServiceImpl birdService;

    @PostMapping("/save")
    public String save(@RequestBody Bird bird){
        birdService.save(bird);
        return "success";
    }

    @DeleteMapping("/deleteById/{bid}")
    public String deleteById(@PathVariable("bid")Long bid){
        birdService.delete(bid);
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody Bird bird){
        birdService.update(bird);
        return "success";
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Page<Bird> findPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return birdService.findPage(pageNum,pageSize);
    }

    @PostMapping("/findAll")
    public List<Bird> findAll(){
        return birdService.findAll();
    }
}
