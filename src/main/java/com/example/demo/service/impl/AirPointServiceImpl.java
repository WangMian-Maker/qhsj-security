package com.example.demo.service.impl;

import com.example.demo.entity.AirPoint;
import com.example.demo.repository.AirPointRepository;
import com.example.demo.service.AirPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AirPointServiceImpl implements AirPointService {
    @Autowired
    private AirPointRepository airPointRepository;
    @Override
    public void save(AirPoint airPoint) {
        airPoint.setAid(airPointRepository.maxId()+1);
        airPointRepository.save(airPoint.getAid(), airPoint.getPoint(),airPoint.getJingWeiDu(),airPoint.getStatus(),airPoint.getEquipIndex(),airPoint.getExampleIndex(),
                airPoint.getImgPath(),airPoint.getCameraPath());
    }

    @Override
    public Boolean update(AirPoint airPoint) {
        if(airPointRepository.findById(airPoint.getAid())==null){
            return false;
        }
        airPointRepository.save(airPoint);
        return true;
    }

    @Override
    public void delete(Long id) {
        airPointRepository.deleteById(id);
    }

    @Override
    public List<AirPoint> findAll() {
        return airPointRepository.findAllByOrder();
    }
}
