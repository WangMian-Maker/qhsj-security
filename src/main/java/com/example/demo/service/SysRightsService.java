package com.example.demo.service;

import com.example.demo.entity.user.SysRights;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysRightsService {
    public void add(SysRights rights);
    public String update(SysRights rights);
    public String deleteById(Long sid);
    public List<SysRights> findAll();
    public Page<SysRights> findAllPage(int pageNum, int pageSize);
    public SysRights findById(Long id);
    public void addTo(Long source,Long to,Integer order);
    public List<SysRights> findRightsByToken(String token);
    //public void set(Long cid ,Long pid);

}
