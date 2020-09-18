package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.events.Event;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    public void createTask(Task task,String token);
    public Boolean update(Task task);
    public void deleteById(Long id);
    public Page<Task> findPage(int pageNum,int pageSize);
    public Task findById(Long tid);
    public List<Event> findAllEvents(Long tid);

    public List<Task> findPage(int pageNum,int pageSize,String worker);
    public List<Task> findPageByStatus(int pageNum,int pageSize,String status,String worker);
    public List<Task> findTaskBelongStaffByToken();
    public List<Task> findPageTaskBelongStaffByTokenAndStatus(int pageNum,int pageSize,String status);
    public void setStaffs(Long tid,List<Long> staffIds);
    public int findCountByStatus(String status);
    public void setLeaders(Long tid,List<Long> staffIds);
    public void setDepartment(Long tid,Long did);
}
