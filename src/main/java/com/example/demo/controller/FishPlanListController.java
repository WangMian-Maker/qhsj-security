package com.example.demo.controller;

import com.example.demo.entity.biology.Bird.BiologyPlan;
import com.example.demo.entity.biology.Bird.BiologyPlanList;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.BiologyPlanListServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("data/fishPlanList")
@Api(tags = "鱼类统计数据")
public class FishPlanListController {
    @Autowired
    private BiologyPlanListServiceImpl birdPlanListService;

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public String save(@RequestBody BiologyPlanList biologyPlanList){
        biologyPlanList.setBiologyType("鱼类");
        birdPlanListService.save(biologyPlanList);
        return "success";
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新")
    public String update(@RequestBody BiologyPlanList biologyPlanList){
        biologyPlanList.setBiologyType("鱼类");
        birdPlanListService.update(biologyPlanList);
        return "success";
    }


    @PostMapping("/findPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询分页")
    public Page<BiologyPlanList> findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return birdPlanListService.findPage(pageNum,pageSize,"鱼类");
    }

    @DeleteMapping("/deleteById/{bid}")
    @ApiOperation(value = "删除")
    public String delete(@PathVariable("bid") Long bid){
        birdPlanListService.deleteById(bid);
        return "success";
    }

    @PostMapping("/addBiologyPlan/{bid}")
    @ApiOperation(value = "添加记录")
    public String  addBirdPlan(@RequestBody BiologyPlan biologyPlan, @PathVariable("bid") Long bid){
        biologyPlan.setBiologyType("鱼类");
        birdPlanListService.addBirdPlan(biologyPlan,bid);
        return "success";
    }
    @PostMapping("/findBiologyPlanPage/{pageNum}/{pageSize}/{bid}")
    @ApiOperation(value = "查询记录分页")
    public Page<BiologyPlan> findBirdPlanPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @PathVariable("bid") Long bid){
        return birdPlanListService.findBirdPlanPage(pageNum,pageSize,bid,"鱼类");
    }
}
