package com.example.demo.service.impl;

import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.user.SysRights;
import com.example.demo.entity.user.SysRole;
import com.example.demo.entity.user.SysUser;
import com.example.demo.config.log.Log;
import com.example.demo.repository.StaffInforRepository;
import com.example.demo.repository.user.SysRightsRepository;
import com.example.demo.repository.user.SysUserRepository;
import com.example.demo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Service
@Transactional
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private SysRightsRepository sysRightsRepository;
    @Autowired
    private StaffInforRepository staffInforRepository;
    @Override
    public void add(SysUser user) {
        user.setSid(getId());
        System.out.println(passwordEncoder.encode(user.getPassword()));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        sysUserRepository.save(user);
    }

    @Override
    public String update(SysUser user,Long staffId) {

        Long id =user.getSid();
        String password=user.getPassword();
        System.out.println(password);
        StaffInfor staffInfor=staffInforRepository.findBystaffId(staffId);
        user.setStaffInfor(staffInfor);
        if(!passwordEncoder.matches(password,sysUserRepository.findBysid(id).getPassword())){
            System.out.println(password);
            password=passwordEncoder.encode(password);
            System.out.println(password);
            user.setPassword(password);
            System.out.println(user.getPassword());
            System.out.println("==================================");
            //sysUserRepository.save(id,password);
        }
        sysUserRepository.save(user);

        return "success";
    }

    @Override
    public String update(SysUser user) {
        sysUserRepository.save(user);
        return "success";
    }

    @Override
    public String deleteById(Long sid) {
        if(sysUserRepository.findById(sid)==null){
            Log.logger.error("找不到此Id的用户");
            return "no this id in table sys_user";
        }
        sysUserRepository.deleteById(sid);
        return "success";
    }

    @Override
    public Page<SysUser> findAllPage(int pageNum, int pageSize) {
        PageRequest request=PageRequest.of(pageNum-1,pageSize);
        return sysUserRepository.findAll(request);
    }

    @Override
    public SysUser findById(Long id) {
        if(sysUserRepository.findBysid(id)==null){
            System.out.println("no this id in table sys_user");
        }
        return sysUserRepository.findBysid(id);
    }

    @Override
    public SysUser findByAccount(String account) {
        if(sysUserRepository.findByaccount(account)==null){
            Log.logger.error("no this account in table sys_user");
        }
        return sysUserRepository.findByaccount(account);
    }

    @Override
    public List<SysUser> findAll() {
        return sysUserRepository.findAll();
    }

    @Override
    public String setStaff(Long staffId,Long sid) {
        //SysUser user=sysUserRepository.findBysid(sid);
        StaffInfor staffInfor=staffInforRepository.findBystaffId(staffId);
        SysUser user=sysUserRepository.findBysid(sid);
        if(user==null||staffInfor==null){
            return "fail";
        }
        user.setStaffInfor(staffInfor);
        sysUserRepository.save(user);
        return "success";
    }

    @Override
    public String setStaff(StaffInfor staffInfor, SysUser user) {
        System.out.println("================================================================="+staffInfor.getStaffName());
        user.setStaffInfor(staffInfor);
        sysUserRepository.save(user);
        return "success";
    }

    @Override
    public StaffInfor findStaff(Long sid) {
        SysUser user=sysUserRepository.findBysid(sid);
        if(user==null){
            return null;
        }
        StaffInfor staffInfor=staffInforRepository.findBystaffId(user.getStaffInfor().getStaffId());
        if(staffInfor==null){
            return null;
        }
        return staffInfor;
    }

    Long getId(){
        List<Long> allId=sysUserRepository.findAllSid();
        if(allId==null||allId.size()==0){
            return 1L;
        }
        for(int i=1;i<allId.size();i++){
            if(allId.get(i)-allId.get(i-1)>1){
                return allId.get(i-1)+1;
            }
        }
        return sysUserRepository.maxId()+1;
    }
}
