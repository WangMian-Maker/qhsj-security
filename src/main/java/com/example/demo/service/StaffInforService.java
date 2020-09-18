package com.example.demo.service;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.user.SysUser;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffInforService {
    public void save(StaffInfor staffInfor,Long departmentId);
    public Boolean update(StaffInfor staffInfor);
    public StaffInfor findById(Long id);
    public Page<StaffInfor> findPage(int pageNum,int pageSize);
    public void deleteById(Long id);
    public List<String> findDepartments();
    public List<StaffInfor> findStaffByStaffName(String name);
    public List<StaffInfor> findStaffByDepartment(String department);
    public List<StaffInfor> findStaffByStaffNameInDepartment(Long did,String name);
    public List<StaffInfor> findStaffByStaffNameInDepartmentAndNoUser(Long did,String name);
}
