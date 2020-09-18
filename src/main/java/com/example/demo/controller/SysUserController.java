package com.example.demo.controller;


import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.user.SysRole;
import com.example.demo.entity.user.SysUser;
import com.example.demo.service.impl.SysRoleServiceImpl;
import com.example.demo.service.impl.SysUserServiceImpl;
import com.fasterxml.jackson.databind.node.LongNode;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("data/user")
@RestController
public class SysUserController {
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private SysRoleServiceImpl sysRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/save/{account}/{password}/{staffId}")
    public String add(@PathVariable("account") String account , @PathVariable("password") String password,@PathVariable("staffId") Long staffId,@RequestBody List<Long> ids){
        SysUser user=new SysUser();
        user.setAccount(account);
        user.setPassword(password);
        List<SysRole> roles=new ArrayList<>();
        for(Long id :ids){
            roles.add(sysRoleService.findById(id));
        }
        if(roles!=null&&roles.size()>0){
            user.setRoles(roles);
        }
        sysUserService.add(user);
        sysUserService.setStaff(staffId,user.getSid());
        return "success";
    }
    @PutMapping("/update/{staffId}")
    public String update(@RequestBody SysUser user,@PathVariable("staffId") Long staffId){
        return sysUserService.update(user,staffId);
        //return "success";
    }
    @DeleteMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") Long id){
        sysUserService.deleteById(id);
        return "success";
    }

    @PostMapping("/findAll")
    public List<SysUser> findAll(){
        return sysUserService.findAll();
    }

    @PostMapping("/findPage/{pageNum}/{pageSize}")
    public Page<SysUser> findPage(@PathVariable("pageNum") int pageNum,@PathVariable("pageSize") int pageSize){
        return sysUserService.findAllPage(pageNum,pageSize);
    }

    @PostMapping("/setRoles/{userId}")
    public String addRoles(@PathVariable("userId") Long userId, @RequestBody List<Long> ids){
        SysUser user=sysUserService.findById(userId);
        List<SysRole> roles=new ArrayList<>();
        for(Long id :ids){
            roles.add(sysRoleService.findById(id));
        }
        if(roles!=null&&roles.size()>0){
            user.setRoles(roles);
        }
        sysUserService.update(user);
        return "success";
    }

    @PostMapping("/findStaffBySelf")
    public StaffInfor findStaffBySelf(){
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserService.findByAccount(userName);
        return sysUserService.findStaff(user.getSid());
    }

//    @PostMapping("/setToStaff/{staffId}/{userId}")
//    public String setToStaff(@PathVariable("staffId") Long staffId,@PathVariable("userId") Long userId){
//        if(sysUserService.giveToStaff(staffId,userId)){
//            return "success";
//        }
//        return "fail";
//    }

    @PostMapping("/findStaff/{userId}")
    public StaffInfor findStaff(@PathVariable("userId") Long userId){
        return sysUserService.findStaff(userId);
    }
}
