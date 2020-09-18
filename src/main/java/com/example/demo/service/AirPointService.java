package com.example.demo.service;

import com.example.demo.entity.AirPoint;

import java.util.List;

public interface AirPointService {
    public void save(AirPoint airPoint);
    public Boolean update(AirPoint airPoint);
    public void delete(Long id);
    public List<AirPoint> findAll();
}
