package com.example.demo.service.impl;

import com.example.demo.config.utils.JwtTokenUtils;
import com.example.demo.entity.Department;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.Task;
import com.example.demo.entity.events.Event;
import com.example.demo.entity.user.SysUser;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.EventRepository;
import com.example.demo.repository.StaffInforRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.user.SysUserRepository;
import com.example.demo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private SysUserRepository sysUserRepository;
    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private StaffInforRepository staffInforRepository;
    @Override
    public void createTask(Task task,String token) {
        if(taskRepository.maxId()==null){
            task.setTid(1l);
        }
        else {
            task.setTid(taskRepository.maxId()+1);
        }
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        task.setCreator(staffInfor);
        task.setStatus("待处理");
        taskRepository.save(task.getTid(),task.getCreateTime(),task.getWeather(),task.getRecode(),task.getDealSuggest(),
                task.getDealResult(),task.getStatus());
    }

    @Override
    public Boolean update(Task task) {
        if(taskRepository.findById(task.getTid())==null){
            return false;
        }
        taskRepository.save(task);
        return true;
    }

    @Override
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Page<Task> findPage(int pageNum, int pageSize) {
        PageRequest request=PageRequest.of(pageNum-1,pageSize);
        return taskRepository.findAll(request);
    }



    @Override
    public Task findById(Long tid) {
        return taskRepository.findBytid(tid);
    }

    @Override
    public List<Event> findAllEvents(Long tid) {
        return eventRepository.findEventInTask(tid);
    }

    @Override
    public List<Task> findPage(int pageNum, int pageSize, String worker) {
        worker="%"+worker+"%";
        return taskRepository.findPage(pageSize,pageNum*pageSize-pageSize,worker);
    }

    @Override
    public List<Task> findPageByStatus(int pageNum, int pageSize, String status, String worker) {
        worker="%"+worker+"%";
        return taskRepository.findBystatus(status,worker,pageSize,pageSize*pageNum-pageSize);
    }

    @Override
    public List<Task> findTaskBelongStaffByToken() {
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        return taskRepository.findByStaffId(staffInfor.getStaffId());
    }


    @Override
    public List<Task> findPageTaskBelongStaffByTokenAndStatus(int pageNum, int pageSize, String status) {
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        status="%"+status+"%";
        return taskRepository.findingByStaffIdAndStatus(staffInfor.getStaffId(),status,pageSize,pageNum*pageSize-pageSize);
    }

    @Override
    public void setStaffs(Long tid,List<Long> staffIds) {
        List<StaffInfor> staffInfors=new ArrayList<>();
        for(int i=0;i<staffIds.size();i++){
            StaffInfor staffInfor=staffInforRepository.findBystaffId(staffIds.get(i));
            if(staffInfor!=null)
                staffInfors.add(staffInfor);
        }
        Task task=taskRepository.findBytid(tid);
        task.setStaffWorkers(staffInfors);
        taskRepository.save(task);
    }

    @Override
    public int findCountByStatus(String status) {
        status="%"+status+"%";
        String[] tmp1=request.getHeader("Authorization").split(" ");
        String token=tmp1[1];
        String userName= JwtTokenUtils.getUsername(token);
        SysUser user=sysUserRepository.findByaccount(userName);
        StaffInfor staffInfor=sysUserService.findStaff(user.getSid());
        return taskRepository.findBystatus(status,staffInfor.getStaffId()).size();
    }

    @Override
    public void setLeaders(Long tid, List<Long> staffIds) {
        List<StaffInfor> staffInfors=new ArrayList<>();
        for(int i=0;i<staffIds.size();i++){
            StaffInfor staffInfor=staffInforRepository.findBystaffId(staffIds.get(i));
            if(staffInfor!=null)
                staffInfors.add(staffInfor);
        }
        Task task=taskRepository.findBytid(tid);
        task.setLeaders(staffInfors);
        taskRepository.save(task);
    }

    @Override
    public void setDepartment(Long tid, Long did) {
        Department department=departmentRepository.findBydid(did);
        Task task=taskRepository.findBytid(tid);
        if(department!=null)
            task.setDepartment(department);
        taskRepository.save(task);
    }

}
