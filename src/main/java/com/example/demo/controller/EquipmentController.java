package com.example.demo.controller;


import com.example.demo.entity.Equipment;
import com.example.demo.entity.params.Page;
import com.example.demo.service.impl.EquipmentServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/data/equipment")
@RestController
@Api(tags = "设备管理")
public class EquipmentController{
    @Autowired
    private EquipmentServiceImpl equipmentService;

    @PostMapping("/save")
    @ApiOperation(value = "保存")
    public String save(@RequestBody Equipment equipment){
        equipmentService.save(equipment);
        return "success";
    }

    @PostMapping("/update")
    @ApiOperation(value = "更新")
    public String update(@RequestBody Equipment equipment){
        equipmentService.update(equipment);
        return "success";
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation(value = "根据id删除")
    public String deleteById(@PathVariable("id") Long id){
        equipmentService.deleteById(id);
        return "success";
    }

    @PostMapping("/findAllPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询所有")
    public Page<Equipment> findAllPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        return equipmentService.findPage("","",pageNum,pageSize);
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    @ApiOperation(value = "分页查询相关")
    public Page<Equipment> findPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,String status,String type){
        return equipmentService.findPage(status,type,pageNum,pageSize);
    }
}
