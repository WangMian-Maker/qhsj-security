package com.example.demo.service.impl;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.StaffInforRepository;
import com.example.demo.service.StaffInforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StaffInforServiceImpl implements StaffInforService {
    @Autowired
    private StaffInforRepository staffInforRepository;
    @Override
    public void save(StaffInfor staffInfor,Long departmentId) {
        if(staffInforRepository.maxId()==null){
            staffInfor.setStaffId(1l);
        }
        else {
            staffInfor.setStaffId(staffInforRepository.maxId()+1);
        }

        staffInforRepository.save(staffInfor.getStaffId(),staffInfor.getStaffName(),staffInfor.getSex(),staffInfor.getBirthday(),staffInfor.getNature(),staffInfor.getOutlook(),
                staffInfor.getPosition(),staffInfor.getBeginWorkTime(),staffInfor.getIdCard(),staffInfor.getPhoneNumber(),staffInfor.getMail(),staffInfor.getMarriage(),staffInfor.getBirthPosition(),
                staffInfor.getCurrentPosition(),staffInfor.getCensusRegister(),departmentId);
    }

    @Override
    public Boolean update(StaffInfor staffInfor) {
        if(staffInforRepository.findById(staffInfor.getStaffId())==null){
            return false;
        }
        staffInforRepository.save(staffInfor);
        return true;
    }

    @Override
    public StaffInfor findById(Long id) {
        return staffInforRepository.findBystaffId(id);
    }

    @Override
    public Page<StaffInfor> findPage(int pageNum, int pageSize) {
        PageRequest request=PageRequest.of(pageNum-1,pageSize);
        return staffInforRepository.findAll(request);
    }

    @Override
    public void deleteById(Long id) {
        staffInforRepository.deleteById(id);
    }

    @Override
    public List<String> findDepartments() {
        return staffInforRepository.findAllDepartment();
    }

    @Override
    public List<StaffInfor> findStaffByStaffName(String name) {
        name="%"+name+"%";
        return staffInforRepository.findByStaffName(name);
    }

    @Override
    public List<StaffInfor> findStaffByDepartment(String department) {
        department="%"+department+"%";
        return staffInforRepository.findByDepartment(department);
    }

    @Override
    public List<StaffInfor> findStaffByStaffNameInDepartment(Long did, String name) {
        name="%"+name+"%";
        return staffInforRepository.findByStaffNameInDepartment(did, name);
    }

    @Override
    public List<StaffInfor> findStaffByStaffNameInDepartmentAndNoUser(Long did, String name) {
        name="%"+name+"%";
        return staffInforRepository.findByStaffNameInDepartment(did,name);
    }

    @Override
    public List<StaffInfor> findAllByStaffNameOrDepartment(String param) {
        if(param==null ||param=="") return null;
        param="%"+param+"%";
        return staffInforRepository.findAllByDepartmentOrStaffName(param);
    }
}
