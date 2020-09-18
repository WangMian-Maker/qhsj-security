package com.example.demo.service;

import com.example.demo.entity.AirData;
import com.example.demo.entity.WaterInfor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AirDataService {
    //public Page<WaterInfor> find(int pageNum, int pageSize);
    //public Page<WaterInfor> findByPoint(String point,int pageNum,int pageSize );
    //public void save(WaterInfor waterInfor);
    //public void delete(Long id);
    //public List<String> findAllPoint();
    //public Long maxId();

    public Page<AirData> find(int pageNum,int pageSize);
    public Page<AirData> findByPoint(String point ,int pageNum,int pageSize);
    public void save(AirData airData);
    //public void deleteByTimeAndPoint(String time,String point );
    public void deleteById(Long id);
    public void update(AirData airData);
}
