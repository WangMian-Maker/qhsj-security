package com.example.demo.service.impl;

import com.example.demo.entity.biology.Amphibious.AmphibiousPlan;
import com.example.demo.entity.biology.Bird.BirdPlan;
import com.example.demo.repository.Amphibious.AmphibiousPlanRepository;
import com.example.demo.repository.Amphibious.AmphibiousRepository;
import com.example.demo.repository.Bird.BirdPlanRepository;
import com.example.demo.repository.Bird.BirdRepository;
import com.example.demo.service.AmphibiousPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AmphibiousPlanServiceImpl implements AmphibiousPlanService {
    @Autowired
    private AmphibiousRepository amphibiousRepository;
    @Autowired
    private AmphibiousPlanRepository amphibiousPlanRepository;

    @Override
    public List<AmphibiousPlan> findByOrder(String order, String planName) {
        List<AmphibiousPlan> allBirdPlans=amphibiousPlanRepository.findByplanName(planName);
        List<AmphibiousPlan> thisOrderBirdPlans=new ArrayList<>();
        for(int i=0 ;i<allBirdPlans.size();i++){
            System.out.println(allBirdPlans.get(i).chineseName+"=======================================");
            System.out.println(amphibiousRepository.findBychineseName(allBirdPlans.get(i).chineseName).getOrderVice()+"=======================================");
            System.out.println(order+"=======================================");
            if(amphibiousRepository.findBychineseName(allBirdPlans.get(i).chineseName).getOrderVice().equals(order)){
                System.out.println(amphibiousRepository.findBychineseName(allBirdPlans.get(i).chineseName).getOrderVice()+"=======================================");
                System.out.println(order+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                thisOrderBirdPlans.add(allBirdPlans.get(i));
            }
        }
        return thisOrderBirdPlans;
    }

    @Override
    public List<AmphibiousPlan> findByFamily(String family,String planName) {
        List<AmphibiousPlan> allBirdPlans=amphibiousPlanRepository.findByplanName(planName);
        List<AmphibiousPlan> thisFamilyBirdPlans=new ArrayList<>();
        for(int i=0;i<allBirdPlans.size();i++){
            if(amphibiousRepository.findBychineseName(allBirdPlans.get(i).chineseName).getFamily().equals(family)){
                thisFamilyBirdPlans.add(allBirdPlans.get(i));
            }
        }
        return thisFamilyBirdPlans;
    }

    @Override
    public AmphibiousPlan findByChineseName(String chineseName,String planName) {
        return amphibiousPlanRepository.findByChineseNameAndPlanName(chineseName,planName);
    }

    @Override
    public Integer findCountInOrder(String order,String planName) {
        List<AmphibiousPlan> birdPlans=findByOrder(order,planName);
        if(birdPlans==null||birdPlans.size()==0){
            return 0;
        }
        Integer sum=0;
        for(int i=0;i<birdPlans.size();i++){
            sum+=Integer.parseInt(birdPlans.get(i).getAmphibiousCount());
        }
        System.out.println(order+":"+sum+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return sum;
    }

    @Override
    public Integer findCountInFamily(String family,String planName) {
        List<AmphibiousPlan> birdPlans=findByFamily(family,planName);
        if(birdPlans==null||birdPlans.size()==0){
            return 0;
        }
        Integer sum=0;
        for(int i=0;i<birdPlans.size();i++){
            sum+=Integer.parseInt(birdPlans.get(i).getAmphibiousCount());
        }
        System.out.println(family+":"+sum+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return sum;
    }

    @Override
    public Integer findCountInChineseName(String chineseName,String planName) {
        if(findByChineseName(chineseName,planName)==null){
            return 0;
        }
        return Integer.parseInt(findByChineseName(chineseName,planName).getAmphibiousCount());
    }

    @Override
    public String findColor3(String order) {
        return amphibiousRepository.findByorderVice(order).get(0).getColor3();
    }

    @Override
    public String findColor2(String family) {
        return amphibiousRepository.findByfamily(family).get(0).getColor2();
    }

    @Override
    public String findColor1(String chineseName) {
        return amphibiousRepository.findBychineseName(chineseName).getColor1();
    }

    @Override
    public void deleteById(Long id) {
        amphibiousPlanRepository.deleteById(id);
    }
}
