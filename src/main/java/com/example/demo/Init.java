package com.example.demo;

import com.example.demo.entity.Department;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.user.SysRights;
import com.example.demo.entity.user.SysRole;
import com.example.demo.entity.user.SysUser;
import com.example.demo.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class Init {
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private SysRoleServiceImpl sysRoleService;
    @Autowired
    private SysRightsServiceImpl sysRightsService;
    @Autowired
    private StaffInforServiceImpl staffInforService;
    @Autowired
    private DepartmentServiceImpl departmentService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostConstruct
    private void init(){
        if(sysRightsService.findAll()==null||sysRightsService.findAll().size()==0){
            SysRights rights=new SysRights();
            rights.setMenuUrl("entryData");
            rights.setM_order("r1");
            rights.setRightsName("数据录入");
            rights.setUrl("/data/**");
            sysRightsService.add(rights);
        }
        SysUser user=sysUserService.findById(1L);
        SysRole role=sysRoleService.findById(1L);
        StaffInfor staffInfor=staffInforService.findById(1L);
        if(staffInfor==null){
            staffInfor=new StaffInfor();
            staffInfor.setStaffId(1L);
            staffInfor.setDepartment(departmentService.findByDid(1L));
            staffInfor.setStaffName("开发者账号");
            staffInforService.save(staffInfor,1L);
        }
        if(role==null){
            role=new SysRole();
            role.setRights(sysRightsService.findAll());
            role.setRoleName("admin");
            role.setSid(1L);
            sysRoleService.add(role);
        }
        if(user==null){
            user=new SysUser();
            user.setSid(1l);
            user.setAccount("admin");
            System.out.println("===================================");
            System.out.println(passwordEncoder.encode("123456"));
            user.setPassword(passwordEncoder.encode("123456"));
            System.out.println(user.getPassword());
            if(role.getRights()==null||role.getRights().size()==0){
                role.setRights(sysRightsService.findAll());
            }
            List<SysRole> roles=new ArrayList<>();
            roles.add(role);
            if(user.getRoles()==null||user.getRoles().size()==0){
                user.setRoles(roles);
            }
            sysRoleService.update(role);
            //sysUserService.add(user);
            //System.out.println("================================================================="+staffInfor.getStaffName());
            sysUserService.setStaff(staffInfor,user);
        }
    }
}
