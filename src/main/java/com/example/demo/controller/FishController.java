package com.example.demo.controller;

import com.example.demo.entity.biology.Bird.Biology;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.BiologyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("data/fish")
@Api(tags = "鱼类基础数据")
public class FishController {
    @Autowired
    private BiologyServiceImpl birdService;

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public String save(@RequestBody Biology biology){
        biology.setBiologyType("鱼类");
        birdService.save(biology);
        return "success";
    }

    @DeleteMapping("/deleteById/{bid}")
    @ApiOperation(value = "删除")
    public String deleteById(@PathVariable("bid")Long bid){
        birdService.delete(bid);
        return "success";
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新")
    public String update(@RequestBody Biology biology){
        biology.setBiologyType("鱼类");
        birdService.update(biology);
        return "success";
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询")
    public Page<Biology> findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return birdService.findPage(pageNum,pageSize,"鱼类");
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "查询所有")
    public List<Biology> findAll(){
        return birdService.findAll("鱼类");
    }
}
