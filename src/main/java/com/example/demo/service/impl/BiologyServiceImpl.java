package com.example.demo.service.impl;

import com.example.demo.entity.ProtectGrade;
import com.example.demo.entity.biology.Bird.Biology;
import com.example.demo.entity.biology.Bird.BiologyPlan;
import com.example.demo.entity.biology.Bird.BiologyPlanList;
import com.example.demo.entity.biology.GradeCount;
import com.example.demo.entity.events.Event;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.Bird.BiologyPlanListRepository;
import com.example.demo.repository.Bird.BiologyPlanRepository;
import com.example.demo.repository.Bird.BiologyRepository;
import com.example.demo.repository.ProtectGradeRepository;
import com.example.demo.service.BiologyService;
import net.sf.json.JSONObject;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BiologyServiceImpl implements BiologyService {
    @Autowired
    private ProtectGradeRepository protectGradeRepository;
    @Autowired
    private BiologyRepository biologyRepository;
    @Autowired
    private BiologyPlanRepository biologyPlanRepository;

    @Autowired
    private BiologyPlanListRepository biologyPlanListRepository;

    @Autowired
    private BiologyPlanServiceImpl biologyPlanService;

    @Override
    public List<String> findAllOrders(String biologyType) {
        return biologyRepository.findAllOrders(biologyType);
    }

    @Override
    public List<String> findAllFamiliesByOrder(String order,String biologyType) {
        return biologyRepository.findAllFamilies(order,biologyType);
    }

    @Override
    public List<String> findAllChineseNameByFamily(String family,String biologyType) {
        return biologyRepository.findAllChineseName(family,biologyType);
    }

    @Override
    public List<Biology> findAll(String biologyType) {
        return biologyRepository.findAll(biologyType);
    }

    @Override
    public void save(Biology biology) {
        Long bid=1L;
        if(biologyRepository.maxId()!=null){
            bid= biologyRepository.maxId()+1;
        }
        biology.setBid(bid);
        biologyRepository.save(biology.getBid(), biology.getChineseName(), biology.getProfessorName(), biology.getProtectGrade().getId(), biology.getColor1(), biology.getFamily(), biology.getFamilyEnglish(), biology.getColor2(),
                biology.getOrderVice(), biology.getOrderViceEnglish(), biology.getColor3(), biology.getInformation(), biology.getExistInformation(),biology.getBiologyType());
    }

    @Override
    public void delete(Long bid) {
        BiologyPlan biologyPlan=biologyPlanRepository.findByBiology(bid);
        if(biologyPlan!=null){
            biologyPlan.setBiology(null);
            biologyPlanRepository.save(biologyPlan);
            biologyPlanService.deleteById(biologyPlan.getBid());
        }
        BiologyPlanList biologyPlanList=biologyPlanListRepository.findByBiology(bid);
        if(biologyPlanList!=null){
            List<Biology> biologies=biologyPlanList.getBiologies();
            Biology biology=biologyRepository.findBybid(bid);
            biologies.remove(biology);
            biologyPlanList.setBiologies(biologies);
            biologyPlanListRepository.save(biologyPlanList);
        }
        biologyRepository.deleteById(bid);

    }

    @Override
    public void update(Biology biology) {
        biologyRepository.save(biology);
    }

    @Override
    public Page<Biology> findPage(int pageNum, int pageSize,String biologyType) {
        List<Biology> biologies = biologyRepository.findPage(pageSize,pageNum*pageSize-pageSize,biologyType);
        Page<Biology> birdPage=new Page<>();
        birdPage.setPageNum(pageNum);
        birdPage.setPageSize(pageSize);
        birdPage.setContent(biologies);
        birdPage.setTotalElements(biologyRepository.findCount(biologyType));
        birdPage.setTotalPages((int)Math.ceil((float)birdPage.getTotalElements()/(float)pageSize));
        return birdPage;
    }

    @Override
    public void uploadImg(List<MultipartFile> imgs,Long id) {

        Biology biology=biologyRepository.findBybid(id);
        addImg(imgs,id);
        biologyRepository.save(biology);
    }

    @Override
    public void save(Biology biology, List<MultipartFile> imgs) {
        Long bid=1L;
        if(biologyRepository.maxId()!=null){
            bid= biologyRepository.maxId()+1;
        }
        biology.setBid(bid);
        biologyRepository.save(biology.getBid(), biology.getChineseName(), biology.getProfessorName(), biology.getProtectGrade().getId(), biology.getColor1(), biology.getFamily(), biology.getFamilyEnglish(), biology.getColor2(),
                biology.getOrderVice(), biology.getOrderViceEnglish(), biology.getColor3(), biology.getInformation(), biology.getExistInformation(),biology.getBiologyType());
        uploadImg(imgs,biology.getBid());
    }

    @Override
    public void addImg(List<MultipartFile> imgs,Long id) {
        Biology biology=biologyRepository.findBybid(id);
        String realPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/biology/"+biology.getChineseName()+"/";
        File photo =new File(realPath);
        int origin=-1;
        if(photo.listFiles()==null||photo.listFiles().length==0){
            origin=0;
        }
        else {
            origin=photo.listFiles().length;
        }

        dealStream(imgs,realPath,".jpg",origin);
    }

    @Override
    public void myDeleteFile(String fileName){
        //System.out.println(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static"+fileName);
        File file=new File(Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static"+fileName);
        try{
            InputStream inputStream=new FileInputStream(file);
            if(inputStream!=null){
                inputStream.close();
            }
            file.delete();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<String> findImgs(Long id) {
        List<String> urls=new ArrayList<String>();
        Biology biology=biologyRepository.findBybid(id);
        String realPath=Thread.currentThread().getContextClassLoader().getResource("").getPath()+"static/biology/"+biology.getChineseName()+"/";
        File photo =new File(realPath);
        if(photo.listFiles()==null||photo.listFiles().length==0){
            return new ArrayList<String>();
        }
        for (int i=0;i< photo.listFiles().length;i++){
            urls.add("/biology/"+biology.getChineseName()+"/"+photo.listFiles()[i].getName());
        }
        return urls;
    }

    @Override
    public GradeCount findCountByGradeAndType(Long grade, String type) {
        String gradeStr=protectGradeRepository.findByGradeSql(grade);
        Integer gradeInt=protectGradeRepository.findByGradeInt(grade);
        GradeCount gradeCount=new GradeCount();
        gradeCount.setGrade(gradeStr);
        gradeCount.setCount(biologyRepository.findCountByGradeAndType(grade,type));
        gradeCount.setGradeInt(gradeInt);
        return gradeCount;
    }

    void dealStream(List<MultipartFile> files,String fileRoot,String type,int origin){

        for(int i=0;i<files.size();i++){
            dealFile(files.get(i),fileRoot,(i+origin)+type);
        }
    }
    void dealFile(MultipartFile file, String fileRoot,String fileName){
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        String photoFilePath=fileRoot+fileName;
        File outFile=new File(photoFilePath);
        if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
            // 创建父文件夹
            outFile.getParentFile().mkdirs();
        }
        if (outFile.getParentFile().getParentFile() != null || !outFile.getParentFile().getParentFile().isDirectory()) {
            // 创建父文件夹
            outFile.getParentFile().getParentFile().mkdirs();
        }
        try {
            fileOutputStream=new FileOutputStream(outFile);
            inputStream=file.getInputStream();
            IOUtils.copy(inputStream,fileOutputStream);
            //System.out.println(photoFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
