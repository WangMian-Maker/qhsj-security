package com.example.demo.service;

import com.example.demo.entity.biology.Amphibious.Amphibious;
import com.example.demo.entity.biology.Bird.Bird;

import java.util.List;

public interface AmphibiousService {
    public List<String> findAllOrders();
    public List<String> findAllFamiliesByOrder(String order);
    public List<String> findAllChineseNameByFamily(String family);
    public List<Amphibious> findAll();
}
