package com.example.demo.service;


import com.example.demo.entity.events.Event;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public interface EventService {
    public void uploadEvent(String eventStr, List<MultipartFile> photoFiles, List<MultipartFile> videoFiles);
    public List<String> findPhotoFilePathById(Long id);
    public List<String> findVideoFilePathById(Long id);
    public Page<Event> findPage(int pageNum,int pageSize);
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
}
