package com.example.demo.service;

import com.example.demo.entity.user.SysRole;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysRoleService {
    public void add(SysRole role);
    public String update(SysRole role);
    public String deleteById(Long sid);
    public List<SysRole> findAll();
    public Page<SysRole> findAllPage(int pageNum,int pageSize);
    public SysRole findById(Long id);
    public SysRole findByRoleName(String roleName);
    public void addRightById(Long roleId,List<Long> ids);
}
