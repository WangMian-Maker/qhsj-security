package com.example.demo.service.impl;

import com.example.demo.entity.AirData;
import com.example.demo.repository.AirDataRepository;
import com.example.demo.service.AirDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AirDataServiceImpl implements AirDataService {
    @Autowired
    private AirDataRepository airDataRepository;
    @Override
    public Page<AirData> find(int pageNum, int pageSize) {
        PageRequest request=PageRequest.of(pageNum-1,pageSize);
        return airDataRepository.findAll(request);
    }

    @Override
    public Page<AirData> findByPoint(String point, int pageNum, int pageSize) {
        Specification<AirData> specification=(root, query, cb)->{
            List<Predicate> predicates=new ArrayList<>();
            predicates.add(cb.equal(root.get("point"), point));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        };
        PageRequest request=PageRequest.of(pageNum-1,pageSize);
        return  airDataRepository.findAll(specification,request);
    }

    @Override
    public void save(AirData airData) {
        Long id=0L;
        if(airDataRepository.findId()==null){
            id=1L;
        }
        else {
            id=airDataRepository.findId()+1;
        }
        airDataRepository.save(id, airData.time, airData.aqi, airData.pm25, airData.pm10, airData.so2, airData.no2, airData.o3, airData.co, airData.point);

        //airDataRepository.save(airData);
    }



    @Override
    public void deleteById(Long id) {
        airDataRepository.deleteById(id);
    }

    @Override
    public void update(AirData airData) {
        airDataRepository.save(airData);
    }

}
