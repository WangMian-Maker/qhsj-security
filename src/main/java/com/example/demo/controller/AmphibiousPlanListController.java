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
@RequestMapping("data/amphibiousPlanList")
@Api(tags = "两栖类统计数据")
public class AmphibiousPlanListController {
    @Autowired
    private BiologyPlanListServiceImpl biologyPlanListService;

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public String save(@RequestBody BiologyPlanList biologyPlanList){
        biologyPlanList.setBiologyType("两栖");
        biologyPlanListService.save(biologyPlanList);
        return "success";
    }

    @PutMapping("/update")
    @ApiOperation(value = "编辑")
    public String update(@RequestBody BiologyPlanList biologyPlanList){
        biologyPlanList.setBiologyType("两栖");
        biologyPlanListService.update(biologyPlanList);
        return "success";
    }


    @PostMapping("/findPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询")
    public Page<BiologyPlanList> findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return biologyPlanListService.findPage(pageNum,pageSize,"两栖");
    }

    @DeleteMapping("/deleteById/{bid}")
    @ApiOperation(value = "删除")
    public String delete(@PathVariable("bid") Long bid){
        biologyPlanListService.deleteById(bid);
        return "success";
    }

    @PostMapping("/addBiologyPlan/{bid}")
    @ApiOperation(value = "添加记录")
    public String  addBiologyPlan(@RequestBody BiologyPlan biologyPlan, @PathVariable("bid") Long bid){
        biologyPlan.setBiologyType("两栖");
        biologyPlanListService.addBirdPlan(biologyPlan,bid);
        return "success";
    }
    @PostMapping("/findBiologyPlanPage/{pageNum}/{pageSize}/{bid}")
    @ApiOperation(value = "分页查询记录")
    public Page<BiologyPlan> findBiologyPlanPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @PathVariable("bid") Long bid){
        return biologyPlanListService.findBirdPlanPage(pageNum,pageSize,bid,"两栖");
    }
}
