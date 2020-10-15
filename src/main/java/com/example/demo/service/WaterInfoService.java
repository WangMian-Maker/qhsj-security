package com.example.demo.service;

import com.example.demo.entity.WaterInfor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WaterInfoService {
    public Page<WaterInfor> find(int pageNum,int pageSize);
    public Page<WaterInfor> findByPoint(String point,int pageNum,int pageSize );
    public void save(WaterInfor waterInfor);
    public void delete(Long id);
    public List<String> findAllPoint();
    public Long maxId();

    public com.example.demo.entity.params.Page<WaterInfor> findPageByPoint(String point, int pageNum, int pageSize);
}
