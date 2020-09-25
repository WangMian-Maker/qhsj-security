package com.example.demo.service;

import com.example.demo.entity.Task;
import com.example.demo.entity.events.Event;
import com.example.demo.entity.params.Page;

import java.util.List;

public interface TaskService {
    public void createTask(Task task);
    public Boolean update(Task task);
    public String deleteById(Long id);
    public Page<Task> findPage(int pageNum,int pageSize);
    public Task findById(Long tid);
    public List<Event> findAllEvents(Long tid);

    public List<Task> findPage(int pageNum, int pageSize, String worker);
    public List<Task> findPageByStatus(int pageNum,int pageSize,String status,String worker);
    public List<Task> findTaskBelongStaffByToken();
    public Page<Task> findPageTaskBelongStaffByTokenAndStatus(int pageNum,int pageSize,String status);
    public void setStaffs(Long tid,List<Long> staffIds);
    public int findCountByStatus(String status);
    public void setLeaders(Long tid,List<Long> staffIds);
    public void setDepartment(Long tid,Long did);
    public void setCreator(Long tid,Long staffId);
}
