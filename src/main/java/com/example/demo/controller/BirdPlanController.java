package com.example.demo.controller;


import com.example.demo.entity.biology.Bird.BiologyPlan;
import com.example.demo.entity.biology.Color;
import com.example.demo.entity.biology.Model;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.BiologyPlanServiceImpl;
import com.example.demo.service.impl.BiologyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("data/birdPlan")
@Api(tags = "鸟类记录")
public class BirdPlanController {

    @Autowired
    private BiologyServiceImpl birdService;

    @Autowired
    private BiologyPlanServiceImpl birdPlanService;

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除")
    public void deleteById(@PathVariable("id") Long id){
        birdPlanService.deleteById(id);
    }


    @GetMapping("/getImage/{planName}")
    @ApiOperation(value = "获取旭日图json")
    public List<Model> getImageForBird(@PathVariable("planName") String planName){
        List<String> allOrders=birdService.findAllOrders("鸟类");
        List<Model> models=new ArrayList<>();
        for(int i=0;i<allOrders.size();i++){
            //List<BirdPlan> thisOrderBirdPlans=birdPlanService.findByOrder(allOrders.get(i));
            Model model=new Model();
            Color color=new Color();
            color.setColor(birdPlanService.findColor3(allOrders.get(i),"鸟类"));
            model.setItemStyle(color);
            model.setValue(birdPlanService.findCountInOrder(allOrders.get(i),planName,"鸟类"));
            model.setName(allOrders.get(i));
            //models.add(model);
            List<Model> models1=new ArrayList<>();
            List<String> allFamilies=birdService.findAllFamiliesByOrder(allOrders.get(i),"鸟类");
            for(int k=0;k<allFamilies.size();k++){
                Model model1=new Model();
                Color color1=new Color();
                color1.setColor(birdPlanService.findColor2(allFamilies.get(k),"鸟类"));
                model1.setItemStyle(color1);
                model1.setValue(birdPlanService.findCountInFamily(allFamilies.get(k),planName,"鸟类"));
                model1.setName(allFamilies.get(k));
                //models1.add(model1);
                List<Model> models2=new ArrayList<>();
                List<String> allChineseName=birdService.findAllChineseNameByFamily(allFamilies.get(k),"鸟类");
                for(int p=0;p<allChineseName.size();p++){
                    Model model2=new Model();
                    Color color2=new Color();
                    color2.setColor(birdPlanService.findColor1(allChineseName.get(p),"鸟类"));
                    model2.setItemStyle(color2);
                    model2.setValue(birdPlanService.findCountInChineseName(allChineseName.get(p),planName));
                    model2.setName(allChineseName.get(p));
                    models2.add(model2);
                }
                model1.setChildren(models2);
                models1.add(model1);
            }
            model.setChildren(models1);
            models.add(model);
        }

        return models;
    }


    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public String save(@RequestBody BiologyPlan biologyPlan){
        biologyPlan.setBiologyType("鸟类");
        birdPlanService.save(biologyPlan);
        return "success";
    }


    @PutMapping("/update")
    @ApiOperation(value = "更新")
    public String update(@RequestBody BiologyPlan biologyPlan){
        biologyPlan.setBiologyType("鸟类");
        birdPlanService.update(biologyPlan);
        return "success";
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询")
    public Page<BiologyPlan> findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return birdPlanService.findPage(pageNum,pageSize,"鸟类");
    }
}
