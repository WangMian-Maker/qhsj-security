package com.example.demo.service;

import com.example.demo.entity.biology.Bird.BiologyPlan;
import com.example.demo.entity.params.Page;

import java.util.List;

public interface BiologyPlanService {
    public List<BiologyPlan> findByOrder(String order, String planName,String biologyType);
    public List<BiologyPlan> findByFamily(String family, String planName,String biologyType);
    public BiologyPlan findByChineseName(String chineseName, String planName);

    public Integer findCountInOrder(String order,String planName,String biologyType);
    public Integer findCountInFamily(String family,String planName,String biologyType);
    public Integer findCountInChineseName(String chineseName,String planName);
    public String findColor3(String order,String biologyType);
    public String findColor2(String family,String biologyType);
    public String findColor1(String chineseName,String biologyType);
    public void deleteById(Long id);

    public void save(BiologyPlan biologyPlan);
    public void update(BiologyPlan biologyPlan);
    public Page<BiologyPlan> findPage(int pageNum, int pageSize,String biologyType);
}
