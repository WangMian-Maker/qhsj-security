package com.example.demo.controller;

import com.example.demo.entity.user.SysRights;
import com.example.demo.entity.user.SysRole;
import com.example.demo.service.impl.SysRightsServiceImpl;
import com.example.demo.service.impl.SysRoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("data/role")
@RestController
public class SysRoleController {
    @Autowired
    private SysRoleServiceImpl sysRoleService;
    @Autowired
    private SysRightsServiceImpl sysRightsService;
    @PostMapping("/save/{roleName}")
    public String add(@PathVariable("roleName") String roleName){
        SysRole role=new SysRole();
        role.setRoleName(roleName);
        sysRoleService.add(role);
        return "success";
    }
    @PutMapping("/update")
    public String update(@RequestBody SysRole role){
        sysRoleService.update(role);
        return "success";
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id){
        sysRoleService.deleteById(id);
        return "success";
    }

    @PostMapping("/findAll")
    public List<SysRole> findAll(){
        return sysRoleService.findAll();
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Page<SysRole> findPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return sysRoleService.findAllPage(pageNum,pageSize);
    }

    @PostMapping("/addRightsById/{roleSid}")
    public String addRightsById(@PathVariable("roleSid") Long roleSid, @RequestBody List<Long> ids){
        //List<SysRights> rights=new ArrayList<>();
        //SysRole role=sysRoleService.findById(roleSid);
        sysRoleService.addRightById(roleSid,ids);
//        for(Long id :ids){
//            SysRights tmpRights=sysRightsService.findById(id);
//            if(tmpRights!=null){
//                rights.add(tmpRights);
//            }
//        }
        //role.setRights(rights);
        //sysRoleService.update(role);
        return "success";
    }

    @PostMapping("/addRightsByBody/{roleSid}")
    public String addRightsByBody(@PathVariable("roleSid")Long roleSid, @RequestBody List<SysRights> rights){
        SysRole role=sysRoleService.findById(roleSid);
        role.setRights(rights);
        sysRoleService.update(role);
        return "success";
    }
}
