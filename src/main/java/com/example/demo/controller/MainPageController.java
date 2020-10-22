package com.example.demo.controller;

import com.example.demo.entity.biology.Bird.Biology;
import com.example.demo.entity.biology.MainPageData;
import com.example.demo.repository.Bird.BiologyRepository;
import com.example.demo.service.impl.BiologyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("data/mainPageData")
@RestController
public class MainPageController {
    @Autowired
    private BiologyRepository biologyRepository;
    @Autowired
    private BiologyServiceImpl biologyService;
    @PostMapping("/findMainPageData")
    public List<MainPageData> findMainPageData(){
        List<Biology> biologies=biologyRepository.findMainPageData();
        List<MainPageData> mainPageData=new ArrayList<>();
        for(Biology biology:biologies){
            MainPageData mainPageData1=new MainPageData(biology.getChineseName(),biology.getProtectGrade().getGrade(),biologyService.findImgs(biology.getBid())==null?null:biologyService.findImgs(biology.getBid()).get(0));
            mainPageData.add(mainPageData1);
        }
        return mainPageData;
    }
}
