package com.example.demo.controller;


import com.example.demo.entity.Camera;
import com.example.demo.entity.Equipment;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.CameraServiceImpl;
import com.example.demo.service.impl.EquipmentServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/data/camera")
@RestController
public class CameraController {
    @Autowired
    private CameraServiceImpl cameraService;

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public String save(@RequestBody Camera camera){
        cameraService.save(camera);
        return "success";
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新")
    public String update(@RequestBody Camera camera){
        cameraService.update(camera);
        return "success";
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除")
    public String deleteById(@PathVariable("id") Long id){
        cameraService.deleteById(id);
        return "success";
    }

    @PostMapping("/findAllPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询所有")
    public Page<Camera> findAllPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return cameraService.findPage(pageNum,pageSize);
    }

    @PostMapping("/findAll")
    @ApiOperation(value = "查询所有")
    public List<Camera> findAll(){
        return cameraService.findAll();
    }
}
