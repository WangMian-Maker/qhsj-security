package com.example.demo.service.impl;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.biology.Bird.Biology;
import com.example.demo.entity.biology.Bird.BiologyPlan;
import com.example.demo.entity.biology.Bird.BiologyPlanList;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.Bird.BiologyPlanListRepository;
import com.example.demo.repository.Bird.BiologyPlanRepository;
import com.example.demo.service.BiologyPlanListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class BiologyPlanListServiceImpl implements BiologyPlanListService {
    @Autowired
    private BiologyPlanListRepository biologyPlanListRepository;
    @Autowired
    private BiologyPlanServiceImpl birdPlanService;
    @Autowired
    private BiologyPlanRepository biologyPlanRepository;
    @Override
    public void save(BiologyPlanList biologyPlanList) {
        Long bid=1L;
        if(biologyPlanListRepository.maxId()!=null){
            bid= biologyPlanListRepository.maxId()+1;
        }
        biologyPlanList.setBid(bid);
        biologyPlanListRepository.save(biologyPlanList.getBid(), biologyPlanList.getTime(), biologyPlanList.getEquipment(),
                biologyPlanList.getIndex(),biologyPlanList.getBiologyType());
        setBirds(biologyPlanList.getBiologies(), biologyPlanList.getBid());
        setWorkers(biologyPlanList.getWorkers(), biologyPlanList.getBid());

    }

    @Override
    public void update(BiologyPlanList biologyPlanList) {
        biologyPlanListRepository.save(biologyPlanList);
    }

    @Override
    public void deleteById(Long bid) {
        biologyPlanListRepository.deleteById(bid);
    }

    @Override
    public Page<BiologyPlanList> findPage(int pageNum, int pageSize,String biologyType) {
        int startPoint =pageNum*pageSize-pageSize;
        Page<BiologyPlanList> birdPlanListPage=new Page<>();
        birdPlanListPage.setContent(biologyPlanListRepository.findPage(pageSize,startPoint,biologyType));
        birdPlanListPage.setPageSize(pageSize);
        birdPlanListPage.setPageNum(pageNum);
        birdPlanListPage.setTotalElements(findAll(biologyType).size());
        birdPlanListPage.setTotalPages((int)Math.ceil((float)birdPlanListPage.getTotalElements()/(float)birdPlanListPage.getPageSize()));
        return birdPlanListPage;
    }

    @Override
    public List<BiologyPlanList> findAll(String biologyType) {
        return biologyPlanListRepository.findAll(biologyType);
    }

    @Override
    public BiologyPlanList findById(Long bid) {
        return biologyPlanListRepository.findBybid(bid);
    }

    @Override
    public void setWorkers(List<StaffInfor> staffInfors,Long bid) {
        BiologyPlanList biologyPlanList =findById(bid);
        biologyPlanList.setWorkers(staffInfors);
        biologyPlanListRepository.save(biologyPlanList);
    }

    @Override
    public void setBirds(List<Biology> biologies, Long bid) {
        BiologyPlanList biologyPlanList =findById(bid);
        biologyPlanList.setBiologies(biologies);
        biologyPlanListRepository.save(biologyPlanList);
    }

    @Override
    public void addBirdPlan(BiologyPlan biologyPlan, Long bid) {

        BiologyPlanList biologyPlanList =findById(bid);
        biologyPlan.setPlanName(biologyPlanList.getIndex());
        birdPlanService.save(biologyPlan);
        List<BiologyPlan> biologyPlans = biologyPlanList.getBiologyPlans();
        biologyPlans.add(biologyPlan);
        biologyPlanList.setBiologyPlans(biologyPlans);
        biologyPlanListRepository.save(biologyPlanList);
    }

    @Override
    public Page<BiologyPlan> findBirdPlanPage(int pageNum, int pageSize, Long bid,String biologyType) {
        List<BiologyPlan> biologyPlans = biologyPlanListRepository.findBybid(bid).getBiologyPlans();
        int startPoint =pageNum*pageSize-pageSize;
        Page<BiologyPlan> birdPlanPage=new Page<>();
        List<Long> bids= biologyPlanListRepository.findPageForbiologyPlan(pageSize,startPoint,bid,biologyType);
        List<BiologyPlan> biologyPlans1 =new ArrayList<>();
        for(Long id:bids){
            BiologyPlan biologyPlan = biologyPlanRepository.findBybid(id);
            biologyPlans1.add(biologyPlan);
        }
        birdPlanPage.setContent(biologyPlans1);
        birdPlanPage.setPageSize(pageSize);
        birdPlanPage.setPageNum(pageNum);
        birdPlanPage.setTotalElements(biologyPlans.size());
        birdPlanPage.setTotalPages((int)Math.ceil((float)birdPlanPage.getTotalElements()/(float)birdPlanPage.getPageSize()));
        return birdPlanPage;
    }
}
