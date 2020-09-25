package com.example.demo.service.impl;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.biology.Bird.BirdPlan;
import com.example.demo.entity.biology.Bird.BirdPlanList;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.Bird.BirdPlanListRepository;
import com.example.demo.repository.Bird.BirdPlanRepository;
import com.example.demo.service.BirdPlanListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class BirdPlanListServiceImpl implements BirdPlanListService {
    @Autowired
    private BirdPlanListRepository birdPlanListRepository;
    @Autowired
    private BirdPlanServiceImpl birdPlanService;
    @Autowired
    private BirdPlanRepository birdPlanRepository;
    @Override
    public void save(BirdPlanList birdPlanList) {
        Long bid=1L;
        if(birdPlanListRepository.maxId()!=null){
            bid=birdPlanListRepository.maxId()+1;
        }
        birdPlanList.setBid(bid);
        birdPlanListRepository.save(birdPlanList.getBid(),birdPlanList.getTime(),birdPlanList.getEquipment(),
                birdPlanList.getIndex());
        setBirds(birdPlanList.getBirds(),birdPlanList.getBid());
        setWorkers(birdPlanList.getWorkers(),birdPlanList.getBid());

    }

    @Override
    public void update(BirdPlanList birdPlanList) {
        birdPlanListRepository.save(birdPlanList);
    }

    @Override
    public void deleteById(Long bid) {
        birdPlanListRepository.deleteById(bid);
    }

    @Override
    public Page<BirdPlanList> findPage(int pageNum, int pageSize) {
        int startPoint =pageNum*pageSize-pageSize;
        Page<BirdPlanList> birdPlanListPage=new Page<>();
        birdPlanListPage.setContent(birdPlanListRepository.findPage(pageSize,startPoint));
        birdPlanListPage.setPageSize(pageSize);
        birdPlanListPage.setPageNum(pageNum);
        birdPlanListPage.setTotalElements(findAll().size());
        birdPlanListPage.setTotalPages((int)Math.ceil(birdPlanListPage.getTotalElements()/birdPlanListPage.getPageSize()));
        return birdPlanListPage;
    }

    @Override
    public List<BirdPlanList> findAll() {
        return birdPlanListRepository.findAll();
    }

    @Override
    public BirdPlanList findById(Long bid) {
        return birdPlanListRepository.findBybid(bid);
    }

    @Override
    public void setWorkers(List<StaffInfor> staffInfors,Long bid) {
        BirdPlanList birdPlanList=findById(bid);
        birdPlanList.setWorkers(staffInfors);
        birdPlanListRepository.save(birdPlanList);
    }

    @Override
    public void setBirds(List<Bird> birds,Long bid) {
        BirdPlanList birdPlanList=findById(bid);
        birdPlanList.setBirds(birds);
        birdPlanListRepository.save(birdPlanList);
    }

    @Override
    public void addBirdPlan(BirdPlan birdPlan,Long bid) {
        birdPlanService.save(birdPlan);
        BirdPlanList birdPlanList=findById(bid);
        List<BirdPlan> birdPlans=birdPlanList.getBirdPlans();
        birdPlans.add(birdPlan);
        birdPlanList.setBirdPlans(birdPlans);
        birdPlanListRepository.save(birdPlanList);
    }

    @Override
    public Page<BirdPlan> findBirdPlanPage(int pageNum, int pageSize, Long bid) {
        List<BirdPlan> birdPlans=birdPlanListRepository.findBybid(bid).getBirdPlans();
        int startPoint =pageNum*pageSize-pageSize;
        Page<BirdPlan> birdPlanPage=new Page<>();
        List<Long> bids=birdPlanListRepository.findPageForBirdPlan(pageSize,startPoint,bid);
        List<BirdPlan> birdPlans1=new ArrayList<>();
        for(Long id:bids){
            BirdPlan birdPlan=birdPlanRepository.findBybid(id);
            birdPlans1.add(birdPlan);
        }
        birdPlanPage.setContent(birdPlans1);
        birdPlanPage.setPageSize(pageSize);
        birdPlanPage.setPageNum(pageNum);
        birdPlanPage.setTotalElements(birdPlans.size());
        birdPlanPage.setTotalPages((int)Math.ceil(birdPlanPage.getTotalElements()/birdPlanPage.getPageSize()));
        return birdPlanPage;
    }
}
