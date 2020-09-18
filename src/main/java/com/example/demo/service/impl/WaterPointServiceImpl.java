package com.example.demo.service.impl;

import com.example.demo.entity.AirPoint;
import com.example.demo.entity.WaterPoint;
import com.example.demo.repository.WaterPointRepository;
import com.example.demo.service.WaterPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WaterPointServiceImpl implements WaterPointService {
    @Autowired
    private WaterPointRepository waterPointRepository;
    @Override
    public void save(WaterPoint waterPoint) {
        waterPoint.setWid(waterPointRepository.maxId()+1);
        waterPointRepository.save(waterPoint.getWid(),waterPoint.getPoint(),waterPoint.getJingWeiDu(),waterPoint.getStatus(),waterPoint.getEquipIndex(),
                waterPoint.getExampleIndex(),waterPoint.getImgPath(),waterPoint.getCameraPath());
    }

    @Override
    public Boolean update(WaterPoint waterPoint) {
        if(waterPointRepository.findById(waterPoint.getWid())==null){
            return  false;
        }
        waterPointRepository.save(waterPoint);
        return true;
    }

    @Override
    public void delete(Long id) {
        waterPointRepository.deleteById(id);
    }

    @Override
    public List<WaterPoint> findAll() {
        return  waterPointRepository.findAllByOrder();
    }
}
