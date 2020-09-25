package com.example.demo.controller;

import com.example.demo.entity.biology.Bird.BirdPlan;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.BirdPlanListServiceImpl;
import com.example.demo.service.impl.BirdPlanServiceImpl;
import com.example.demo.entity.biology.Bird.BirdPlanList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("data/birdPlanList")
public class BirdPlanListController {
    @Autowired
    private BirdPlanListServiceImpl birdPlanListService;

    @PostMapping("/save")
    public String save(@RequestBody BirdPlanList birdPlanList){
        birdPlanListService.save(birdPlanList);
        return "success";
    }

    @PutMapping("/update")
    public String update(@RequestBody BirdPlanList birdPlanList){
        birdPlanListService.update(birdPlanList);
        return "success";
    }


    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Page<BirdPlanList> fondPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return birdPlanListService.findPage(pageNum,pageSize);
    }

    @DeleteMapping("/deleteById/{bid}")
    public String delete(@PathVariable("bid") Long bid){
        birdPlanListService.deleteById(bid);
        return "success";
    }

    @PostMapping("/addBirdPlan/{bid}")
    public String  addBirdPlan(@RequestBody BirdPlan birdPlan, @PathVariable("bid") Long bid){
        birdPlanListService.addBirdPlan(birdPlan,bid);
        return "success";
    }
    @PostMapping("/findBirdPlanPage/{pageNum}/{pageSize}/{bid}")
    public Page<BirdPlan> findBirdPlanPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize,@PathVariable("bid") Long bid){
        return birdPlanListService.findBirdPlanPage(pageNum,pageSize,bid);
    }
}
