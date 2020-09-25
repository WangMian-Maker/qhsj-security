package com.example.demo.service;


import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.params.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BirdService {
    public List<String> findAllOrders();
    public List<String> findAllFamiliesByOrder(String order);
    public List<String> findAllChineseNameByFamily(String family);
    public List<Bird> findAll();

    public void save(Bird bird);
    public void delete(Long bid);
    public void update(Bird bird);
    public Page<Bird> findPage(int pageNum,int pageSize);
}
