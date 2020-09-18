package com.example.demo.controller;

import com.example.demo.entity.AreaInfor;
import com.example.demo.entity.ParkInformation;
import com.example.demo.repository.AreaInforRepository;
import com.example.demo.repository.ParkInformationRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequestMapping("data")
@RestController
public class ParkInformationController {
    @Autowired
    private ParkInformationRepository parkInformationRepository;
    @Autowired
    private AreaInforRepository areaInforRepository;


    @ApiOperation(value = "bu yao yong zhe ge")
    @GetMapping("/parkInfor/save/{parkSize}/{waterSize}/{waterPer}")
    public void save(@PathVariable("parkSize") String parkSize,@PathVariable("waterSize") String waterSize,@PathVariable("waterPer") String waterPer){
        ParkInformation parkInformation=new ParkInformation();
        parkInformation.setPid(1l);

        parkInformation.setParkSize(parkSize);
        parkInformation.setWaterSize(waterSize);
        parkInformation.setWaterPer(waterPer);
        parkInformationRepository.save(parkInformation);
    }

    @GetMapping("/parkInfor/getParkInfor")
    public ParkInformation getParkInformation(){
        return parkInformationRepository.find();
    }
    @GetMapping("/parkInfor/addAreaType/{type}/{areaSize}/{per}")
    public void addAreaType(@PathVariable("type") String type, @PathVariable("areaSize") String areaSize,@PathVariable("per")String per){
        ParkInformation parkInformation=parkInformationRepository.find();
        AreaInfor areaInfor=new AreaInfor();
        if(areaInforRepository.findBytype(type)!=null){
            areaInfor=areaInforRepository.findBytype(type);
        }
        areaInfor.setType(type);
        areaInfor.setSize(areaSize);
        areaInfor.setPer(per);
        Set<AreaInfor> areaInfors=parkInformation.getAreaType();
        areaInfors.add(areaInfor);
        parkInformation.setAreaType(areaInfors);
        areaInforRepository.save(areaInfor);
        parkInformationRepository.save(parkInformation);
    }

    @GetMapping("/parkInfor/addFunctionType/{type}/{areaSize}/{per}")
    public void addFunctionType(@PathVariable("type") String type, @PathVariable("areaSize") String areaSize,@PathVariable("per")String per){
        ParkInformation parkInformation=parkInformationRepository.find();
        AreaInfor areaInfor=new AreaInfor();
        if(areaInforRepository.findBytype(type)!=null){
            areaInfor=areaInforRepository.findBytype(type);
        }
        areaInfor.setType(type);
        areaInfor.setSize(areaSize);
        areaInfor.setPer(per);
        Set<AreaInfor> areaInfors=parkInformation.getFunctionType();
        areaInfors.add(areaInfor);
        parkInformation.setFunctionType(areaInfors);
        areaInforRepository.save(areaInfor);
        parkInformationRepository.save(parkInformation);
    }

    @GetMapping("/parkInfor/deleteAreaTypeByType/{type}")
    public void deleteAreaTypeByType(@PathVariable("type") String type){
        ParkInformation parkInformation=parkInformationRepository.find();
        Set<AreaInfor> areaInfors=parkInformation.getAreaType();

        for(AreaInfor a:areaInfors){
            if(a.getType().equals(type) ){
                System.out.println(a.getType()+"================================");
                areaInfors.remove(a);
                break;
            }
        }
        parkInformation.setAreaType(areaInfors);
        parkInformationRepository.save(parkInformation);
    }

    @GetMapping("/parkInfor/deleteFunctionTypeByType/{type}")
    public void deleteFunctionTypeByType(@PathVariable("type") String type){
        ParkInformation parkInformation=parkInformationRepository.find();
        Set<AreaInfor> areaInfors=parkInformation.getFunctionType();
        for(AreaInfor a:areaInfors){
            System.out.println(a.getType()+"================================");
            System.out.println(type+"================================");
            if(a.getType().equals(type)){
                System.out.println(a.getType()+"================================");
                areaInfors.remove(a);
                break;
            }
        }
        parkInformation.setFunctionType(areaInfors);
        parkInformationRepository.save(parkInformation);
    }

    @PutMapping("/parkInfor/update")
    public String update(@RequestBody AreaInfor AreaInfor){
        areaInforRepository.save(AreaInfor);
        return "success";
    }

}
