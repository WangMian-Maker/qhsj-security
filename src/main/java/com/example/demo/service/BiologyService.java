package com.example.demo.service;


import com.example.demo.entity.biology.Bird.Biology;
import com.example.demo.entity.params.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BiologyService {
    public List<String> findAllOrders(String biologyType);
    public List<String> findAllFamiliesByOrder(String order,String biologyType);
    public List<String> findAllChineseNameByFamily(String family,String biologyType);
    public List<Biology> findAll(String biologyType);

    public void save(Biology biology);
    public void delete(Long bid);
    public void update(Biology biology);
    public Page<Biology> findPage(int pageNum, int pageSize,String biologyType);
    public void uploadImg(List<MultipartFile> imgs,Long id);
    public void save(Biology biology,List<MultipartFile> imgs);
    public void addImg(List<MultipartFile> imgs,Long id);
    public void myDeleteFile(String filePath);
    public List<String> findImgs(Long id);
}
