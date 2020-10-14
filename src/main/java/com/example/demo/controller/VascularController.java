package com.example.demo.controller;


import com.example.demo.entity.biology.Bird.Biology;
import com.example.demo.entity.biology.GradeCount;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.BiologyServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("data/vascular")
@Api(tags = "维管植物基础数据")
public class VascularController {
    @Autowired
    private BiologyServiceImpl birdService;

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public String save(@RequestBody Biology biology){
        biology.setBiologyType("维管植物");
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
        biology.setBiologyType("维管植物");
        birdService.update(biology);
        return "success";
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询")
    public Page<Biology> findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return birdService.findPage(pageNum,pageSize,"维管植物");
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "查询所有")
    public List<Biology> findAll(){
        return birdService.findAll("维管植物");
    }

    @PostMapping("/saveWithImg")
    @ApiOperation(value = "保存(带图片)")
    public String saveWithImg(@RequestPart("biology") String biologyBJson, @RequestPart("imgs")List<MultipartFile> imgs){
        Biology biology= (Biology) JSONObject.toBean(new JSONObject(biologyBJson),Biology.class);
        biology.setBiologyType("维管植物");
        birdService.save(biology,imgs);
        return "success";
    }

    @PostMapping("/addImgs")
    @ApiOperation(value = "添加图片")
    public String addImgs(@RequestPart("id") String id, @RequestPart("imgs")List<MultipartFile> imgs){
        Long tmpId=Long.valueOf(id);
        birdService.addImg(imgs,tmpId);
        return "success";
    }

    @PostMapping("/deleteImg")
    @ApiOperation(value = "删除图片(url为图片去掉ip port的路径)")
    public String deleteImg(@RequestBody String url){
        JSONObject object=JSONObject.fromObject(url);
        birdService.myDeleteFile(object.getString("url"));
        return "success";
    }

    @PostMapping("/findImgs/{id}")
    @ApiOperation(value = "查询图片")
    public  List<String> findImgs(@PathVariable("id") Long id){
        return birdService.findImgs(id);
    }

    @PostMapping("/findCount")
    @ApiOperation(value = "获取各个保护等级数量,在数组中的顺序为 国家Ⅰ类 国家Ⅱ级 省级 ")
    public List<GradeCount> findCount(){
        List<GradeCount> tmp=new ArrayList<>();
        tmp.add(birdService.findCountByGradeAndType(1L,"维管植物"));
        tmp.add(birdService.findCountByGradeAndType(2L,"维管植物"));
        tmp.add(birdService.findCountByGradeAndType(3L,"维管植物"));
        tmp.add(birdService.findCountByGradeAndType(4L,"维管植物"));
        return tmp;
    }
}
