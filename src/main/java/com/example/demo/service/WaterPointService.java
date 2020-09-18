package com.example.demo.service;

import com.example.demo.entity.AirPoint;
import com.example.demo.entity.WaterPoint;

import java.util.List;

public interface WaterPointService {
    public void save(WaterPoint waterPoint);
    public Boolean update(WaterPoint waterPoint);
    public void delete(Long id);
    public List<WaterPoint> findAll();
}
