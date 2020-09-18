package com.example.demo.service.impl;


import com.example.demo.entity.biology.Bird.BirdPlan;
import com.example.demo.repository.Bird.BirdPlanRepository;
import com.example.demo.repository.Bird.BirdRepository;
import com.example.demo.service.BirdPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BirdPlanServiceImpl implements BirdPlanService {

    @Autowired
    private BirdRepository birdRepository;
    @Autowired
    private BirdPlanRepository birdPlanRepository;

    @Override
    public List<BirdPlan> findByOrder(String order,String planName) {
        List<BirdPlan> allBirdPlans=birdPlanRepository.findByplanName(planName);
        List<BirdPlan> thisOrderBirdPlans=new ArrayList<>();
        for(int i=0 ;i<allBirdPlans.size();i++){
            System.out.println(allBirdPlans.get(i).chineseName+"=======================================");
            System.out.println(birdRepository.findBychineseName(allBirdPlans.get(i).chineseName).getOrderVice()+"=======================================");
            System.out.println(order+"=======================================");
            if(birdRepository.findBychineseName(allBirdPlans.get(i).chineseName).getOrderVice().equals(order)){
                System.out.println(birdRepository.findBychineseName(allBirdPlans.get(i).chineseName).getOrderVice()+"=======================================");
                System.out.println(order+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                thisOrderBirdPlans.add(allBirdPlans.get(i));
            }
        }
        return thisOrderBirdPlans;
    }

    @Override
    public List<BirdPlan> findByFamily(String family,String planName) {
        List<BirdPlan> allBirdPlans=birdPlanRepository.findByplanName(planName);
        List<BirdPlan> thisFamilyBirdPlans=new ArrayList<>();
        for(int i=0;i<allBirdPlans.size();i++){
            if(birdRepository.findBychineseName(allBirdPlans.get(i).chineseName).getFamily().equals(family)){
                thisFamilyBirdPlans.add(allBirdPlans.get(i));
            }
        }
        return thisFamilyBirdPlans;
    }

    @Override
    public BirdPlan findByChineseName(String chineseName,String planName) {
        return birdPlanRepository.findByChineseNameAndPlanName(chineseName,planName);
    }

    @Override
    public Integer findCountInOrder(String order,String planName) {
        List<BirdPlan> birdPlans=findByOrder(order,planName);
        if(birdPlans==null||birdPlans.size()==0){
            return 0;
        }
        Integer sum=0;
        for(int i=0;i<birdPlans.size();i++){
            sum+=Integer.parseInt(birdPlans.get(i).getBirdCount());
        }
        System.out.println(order+":"+sum+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return sum;
    }

    @Override
    public Integer findCountInFamily(String family,String planName) {
        List<BirdPlan> birdPlans=findByFamily(family,planName);
        if(birdPlans==null||birdPlans.size()==0){
            return 0;
        }
        Integer sum=0;
        for(int i=0;i<birdPlans.size();i++){
            sum+=Integer.parseInt(birdPlans.get(i).getBirdCount());
        }
        System.out.println(family+":"+sum+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return sum;
    }

    @Override
    public Integer findCountInChineseName(String chineseName,String planName) {
        if(findByChineseName(chineseName,planName)==null){
            return 0;
        }
        return Integer.parseInt(findByChineseName(chineseName,planName).getBirdCount());
    }

    @Override
    public String findColor3(String order) {
        return birdRepository.findByorderVice(order).get(0).getColor3();
    }

    @Override
    public String findColor2(String family) {
        return birdRepository.findByfamily(family).get(0).getColor2();
    }

    @Override
    public String findColor1(String chineseName) {
        return birdRepository.findBychineseName(chineseName).getColor1();
    }

    @Override
    public void deleteById(Long id) {
        birdPlanRepository.deleteById(id);
    }
}
