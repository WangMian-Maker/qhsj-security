package com.example.demo.service;

import com.example.demo.entity.biology.Bird.BirdPlan;

import java.util.List;

public interface BirdPlanService {
    public List<BirdPlan> findByOrder(String order,String planName);
    public List<BirdPlan> findByFamily(String family,String planName);
    public BirdPlan findByChineseName(String chineseName,String planName);

    public Integer findCountInOrder(String order,String planName);
    public Integer findCountInFamily(String family,String planName);
    public Integer findCountInChineseName(String chineseName,String planName);
    public String findColor3(String order);
    public String findColor2(String family);
    public String findColor1(String chineseName);
    public void deleteById(Long id);
}
