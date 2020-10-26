package com.example.demo.service;


import com.example.demo.entity.Department;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.Task;
import com.example.demo.entity.events.Event;
import com.example.demo.entity.params.Page;
import org.apache.poi.ss.formula.functions.Even;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface EventService {
    public Long uploadEvent(String eventStr, List<MultipartFile> photoFiles, List<MultipartFile> videoFiles);
    public List<String> findPhotoFilePathById(Long id);
    public List<String> findVideoFilePathById(Long id);
    public Page<Event> findPage(int pageNum, int pageSize);
    public void delete(Long id) throws IOException;
    public void myDeleteFile(String url)throws IOException;
    public void videoPlay(Long id, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException;
    public void addPhotoForEvent(Long id, List<MultipartFile> photoFiles);
    public void addVideoForEvent(Long id, List<MultipartFile> photoFiles);
    public Boolean update(Event event);
    public String uploadEventToTask(Long taskId,String eventStr, List<MultipartFile> photoFiles, List<MultipartFile> videoFiles);
    public List<Event> findByTaskAndStatus(Long tid,String status);
    public List<Event> findByFindPersonAndStatus(String status);
    public List<Integer> findCountInTask(Long tid);
    public List<Integer> findCountInFindPersonByToken();
    public void setDepartment(Department department,Long eid);
    public void setChargePerson(StaffInfor staffInfor,Long eid);
    public void setFindPerson(StaffInfor staffInfor,Long eid);
    public void setDealPerson(StaffInfor staffInfor,Long eid);
    public void setOperationPerson(StaffInfor staffInfor,Long eid);
    public void setTask(Task task,Long eid);


    public void setDepartment(Long did,Long eid);
    public void setChargePerson(Long staffId,Long eid);
    public void setFindPerson(Long staffId,Long eid);
    public void setDealPerson(Long staffId,Long eid);
    public void setOperationPerson(Long staffId,Long eid);
    public void setTask(Long tid,Long eid);
    public Event findById(Long id);

    public List<Event> findAllEventNoTask();
    public Page<Event> findFirstPageNoTask(String status);
    public List<Event> findNewPageNoTask(Long currentTime,String status);
    public List<Event> findOldPageNoTask(Long lastTime,String status,int size);
}
