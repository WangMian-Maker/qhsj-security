package com.example.demo.service;


import com.example.demo.entity.biology.Bird.Bird;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BirdService {
    public List<String> findAllOrders();
    public List<String> findAllFamiliesByOrder(String order);
    public List<String> findAllChineseNameByFamily(String family);
    public List<Bird> findAll();
}
