package com.example.demo.service;


import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.user.SysRights;
import com.example.demo.entity.user.SysUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysUserService {

    public void add(SysUser user);
    public String update(SysUser user,Long staffId);
    public String update(SysUser user);
    public String deleteById(Long sid);
    public Page<SysUser> findAllPage(int pageNum,int pageSize);
    public SysUser findById(Long id);
    public SysUser findByAccount(String account);
    public List<SysUser> findAll();
    public String setStaff(Long staffId,Long sid);
    public String setStaff(StaffInfor staffInfor,SysUser user);
    public StaffInfor findStaff(Long sid);
    //public List<SysRights> findRightsByToken(String token);
    //public Page<SysUser> findPageByRole(String role,int pageNum,int pageSize);
}
