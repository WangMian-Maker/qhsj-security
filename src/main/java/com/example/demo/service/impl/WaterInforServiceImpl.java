package com.example.demo.service.impl;

import com.example.demo.entity.WaterInfor;
import com.example.demo.repository.WaterInforRepository;
import com.example.demo.service.WaterInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WaterInforServiceImpl implements WaterInfoService {
    @Autowired
    private WaterInforRepository waterInforRepository;
    @Override
    public Page<WaterInfor> find(int pageNum, int pageSize) {
        PageRequest pageRequest=PageRequest.of(pageNum-1,pageSize);
        return waterInforRepository.findAll(pageRequest);
    }

    // tian jian fen ye cha xun
    @Override
    public Page<WaterInfor> findByPoint(String point, int pageNum, int pageSize) {
        PageRequest pageRequest=PageRequest.of(pageNum-1,pageSize);
        Specification<WaterInfor> specification=(root,query,cb)->{
            List<Predicate> predicates=new ArrayList<>();
            predicates.add(cb.equal(root.get("point"), point));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        return waterInforRepository.findAll(specification,pageRequest);
    }

    @Override
    public void save(WaterInfor waterInfor) {
        waterInforRepository.save(waterInfor);
    }

    @Override
    public void delete(Long id) {
        waterInforRepository.deleteById(id);
    }

    @Override
    public List<String> findAllPoint() {
        return waterInforRepository.findAllPoint();
    }

    @Override
    public Long maxId() {
        return waterInforRepository.maxId()+1;
    }
}
