package com.example.demo.service;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.biology.Bird.Biology;
import com.example.demo.entity.biology.Bird.BiologyPlan;
import com.example.demo.entity.biology.Bird.BiologyPlanList;
import com.example.demo.entity.params.Page;

import java.util.List;

public interface BiologyPlanListService {
    public void save(BiologyPlanList biologyPlanList);
    public void update(BiologyPlanList biologyPlanList);
    public void deleteById(Long bid);
    public Page<BiologyPlanList> findPage(int pageNum, int pageSize,String biologyType);
    public List<BiologyPlanList> findAll(String biologyType);
    public BiologyPlanList findById(Long bid);

    public void setWorkers(List<StaffInfor> staffInfors,Long bid);
    public void setBirds(List<Biology> biologies, Long bid);
    public void addBirdPlan(BiologyPlan biologyPlan, Long bid);
    public Page<BiologyPlan> findBirdPlanPage(int pageNum, int pageSize, Long bid,String biologyType);
}
