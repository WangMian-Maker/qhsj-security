package com.example.demo.controller;

import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.LoginInfor;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.user.Login;
import com.example.demo.entity.user.SysUser;
import com.example.demo.service.impl.LoginServiceImpl;
import com.example.demo.service.impl.SysUserServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private LoginServiceImpl loginService;
    @Autowired
    private HttpServletRequest request;
    @PostMapping("myLogin")
    public String GetToken(@RequestBody Login login){
        return loginService.getToken(login);
    }

    @PostMapping("haoLogin")
    public LoginInfor GetHaoToken(@RequestBody Login login){
        return loginService.getTokenEntity(login);
    }

    @PostMapping("/data/getStaffInforByToken")
    public String getStaffInforByToken(){
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserService.findByAccount(userName);
        System.out.println("============================="+user.getSid());
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        System.out.println(staffInfor.getBirthday()+"===================================");
        System.out.println(JSONArray.fromObject(staffInfor).toString()+"wode");
        return JSONObject.fromObject(staffInfor).toString();
    }

    @PostMapping("/data/test")
    public String test(){
        return "success";
    }

    @PostMapping("/test")
    public String test1(){
        return "success";
    }
}
