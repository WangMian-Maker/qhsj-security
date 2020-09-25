package com.example.demo.service;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.biology.Bird.BirdPlan;
import com.example.demo.entity.biology.Bird.BirdPlanList;
import com.example.demo.entity.params.Page;

import java.util.List;

public interface BirdPlanListService {
    public void save(BirdPlanList birdPlanList);
    public void update(BirdPlanList birdPlanList);
    public void deleteById(Long bid);
    public Page<BirdPlanList> findPage(int pageNum,int pageSize);
    public List<BirdPlanList> findAll();
    public BirdPlanList findById(Long bid);

    public void setWorkers(List<StaffInfor> staffInfors,Long bid);
    public void setBirds(List<Bird> birds,Long bid);
    public void addBirdPlan(BirdPlan birdPlan,Long bid);
    public Page<BirdPlan> findBirdPlanPage(int pageNum,int pageSize,Long bid);
}
