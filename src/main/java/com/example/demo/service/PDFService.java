package com.example.demo.service;

import com.example.demo.entity.PDF;
import com.example.demo.entity.params.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PDFService {
    public boolean save(PDF pdf, MultipartFile pdfFile);
    public void update(PDF pdf);
    public void deleteById(Long id);
    public Page<PDF> findPage(int pageNum,int pageSize);
    public List<PDF> findAll();
    public PDF findById(Long id);

    public String findPath(Long id);
}
