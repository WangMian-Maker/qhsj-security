package com.example.demo.service.impl;


import com.example.demo.entity.biology.Bird.BiologyPlan;
import com.example.demo.entity.biology.Bird.BiologyPlanList;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.Bird.BiologyPlanListRepository;
import com.example.demo.repository.Bird.BiologyPlanRepository;
import com.example.demo.repository.Bird.BiologyRepository;
import com.example.demo.service.BiologyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BiologyPlanServiceImpl implements BiologyPlanService {

    @Autowired
    private BiologyRepository biologyRepository;
    @Autowired
    private BiologyPlanRepository biologyPlanRepository;
    @Autowired
    private BiologyPlanListRepository biologyPlanListRepository;

    @Override
    public List<BiologyPlan> findByOrder(String order, String planName,String biologyType) {
        List<BiologyPlan> allBiologyPlans = biologyPlanRepository.findByplanName(planName);
        List<BiologyPlan> thisOrderBiologyPlans =new ArrayList<>();
        for(int i = 0; i< allBiologyPlans.size(); i++){
            //System.out.println(allBiologyPlans.get(i).chineseName+"=======================================");
            //System.out.println(biologyRepository.findBychineseName(allBiologyPlans.get(i).chineseName,biologyType).getOrderVice()+"=======================================");
            //System.out.println(order+"=======================================");
            if(biologyRepository.findBychineseName(allBiologyPlans.get(i).chineseName,biologyType).getOrderVice().equals(order)){
                System.out.println(biologyRepository.findBychineseName(allBiologyPlans.get(i).chineseName,biologyType).getOrderVice()+"=======================================");
                System.out.println(order+"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                thisOrderBiologyPlans.add(allBiologyPlans.get(i));
            }
        }
        return thisOrderBiologyPlans;
    }

    @Override
    public List<BiologyPlan> findByFamily(String family, String planName,String biologyType) {
        List<BiologyPlan> allBiologyPlans = biologyPlanRepository.findByplanName(planName);
        List<BiologyPlan> thisFamilyBiologyPlans =new ArrayList<>();
        for(int i = 0; i< allBiologyPlans.size(); i++){
            if(biologyRepository.findBychineseName(allBiologyPlans.get(i).chineseName,biologyType).getFamily().equals(family)){
                thisFamilyBiologyPlans.add(allBiologyPlans.get(i));
            }
        }
        return thisFamilyBiologyPlans;
    }

    @Override
    public BiologyPlan findByChineseName(String chineseName, String planName) {
        return biologyPlanRepository.findByChineseNameAndPlanName(chineseName,planName);
    }

    @Override
    public Integer findCountInOrder(String order,String planName,String biologyType) {
        List<BiologyPlan> biologyPlans =findByOrder(order,planName,biologyType);
        if(biologyPlans ==null|| biologyPlans.size()==0){
            return 0;
        }
        Integer sum=0;
        for(int i = 0; i< biologyPlans.size(); i++){
            sum+=Integer.parseInt(biologyPlans.get(i).getBiologyCount());
        }
        System.out.println(order+":"+sum+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return sum;
    }

    @Override
    public Integer findCountInFamily(String family,String planName,String biologyType) {
        List<BiologyPlan> biologyPlans =findByFamily(family,planName,biologyType);
        if(biologyPlans ==null|| biologyPlans.size()==0){
            return 0;
        }
        Integer sum=0;
        for(int i = 0; i< biologyPlans.size(); i++){
            sum+=Integer.parseInt(biologyPlans.get(i).getBiologyCount());
        }
        System.out.println(family+":"+sum+"XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return sum;
    }

    @Override
    public Integer findCountInChineseName(String chineseName,String planName) {
        if(findByChineseName(chineseName,planName)==null){
            return 0;
        }
        return Integer.parseInt(findByChineseName(chineseName,planName).getBiologyCount());
    }

    @Override
    public String findColor3(String order,String biologyType) {
        return biologyRepository.findByorderVice(order,biologyType).get(0).getColor3();
    }

    @Override
    public String findColor2(String family,String biologyType) {
        return biologyRepository.findByfamily(family,biologyType).get(0).getColor2();
    }

    @Override
    public String findColor1(String chineseName,String biologyType) {
        return biologyRepository.findBychineseName(chineseName,biologyType).getColor1();
    }

    @Override
    public void deleteById(Long id) {
        BiologyPlanList biologyPlanList=biologyPlanListRepository.findByBiologyPlan(id);
        BiologyPlan biologyPlan=biologyPlanRepository.findBybid(id);
        if(biologyPlanList!=null){
            List<BiologyPlan> biologyPlans=biologyPlanList.getBiologyPlans();
            biologyPlans.remove(biologyPlan);
            biologyPlanList.setBiologyPlans(biologyPlans);
            biologyPlanListRepository.save(biologyPlanList);
        }
        biologyPlanRepository.deleteById(id);
    }

    @Override
    public void save(BiologyPlan biologyPlan) {
        Long bid=1L;
        if(biologyPlanRepository.maxId()!=null){
            bid= biologyPlanRepository.maxId()+1;
        }
        biologyPlan.setBid(bid);
        System.out.println(biologyPlan.getLindex());
        System.out.println(biologyPlan.getPindex());
        System.out.println(biologyPlan.getOther());
        System.out.println(biologyPlan.getBiology().getBid());
        System.out.println(biologyPlan.getBiologyType());
        biologyPlanRepository.save(biologyPlan.getBid(), biologyPlan.getBiologyCount(), biologyPlan.getWatchCount(),
                biologyPlan.getLindex(), biologyPlan.getPindex(), biologyPlan.getOther(), biologyPlan.getBiology().getBid(),biologyPlan.getBiologyType(),biologyPlan.getPlanName());
    }

    @Override
    public void update(BiologyPlan biologyPlan) {
        //biologyPlan.getBiology().setBiologyType(biologyPlan.getBiologyType());
        biologyPlanRepository.save(biologyPlan);
    }

    @Override
    public Page<BiologyPlan> findPage(int pageNum, int pageSize,String biologyType) {
        List<BiologyPlan> birds= biologyPlanRepository.findPage(pageSize,pageNum*pageSize-pageSize,biologyType);
        Page<BiologyPlan> birdPage=new Page<>();
        birdPage.setPageNum(pageNum);
        birdPage.setPageSize(pageSize);
        birdPage.setContent(birds);
        birdPage.setTotalElements(biologyPlanRepository.findCount(biologyType));
        birdPage.setTotalPages((int)Math.ceil((float)birdPage.getTotalElements()/(float)pageSize));
        return birdPage;
    }
}
