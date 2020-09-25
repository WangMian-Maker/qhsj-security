package com.example.demo.service.impl;

import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.Bird.BirdRepository;
import com.example.demo.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BirdServiceImpl implements BirdService {
    @Autowired
    private BirdRepository birdRepository;

    @Override
    public List<String> findAllOrders() {
        return birdRepository.findAllOrders();
    }

    @Override
    public List<String> findAllFamiliesByOrder(String order) {
        return birdRepository.findAllFamilies(order);
    }

    @Override
    public List<String> findAllChineseNameByFamily(String family) {
        return birdRepository.findAllChineseName(family);
    }

    @Override
    public List<Bird> findAll() {
        return birdRepository.findAll();
    }

    @Override
    public void save(Bird bird) {
        Long bid=1L;
        if(birdRepository.maxId()!=null){
            bid=birdRepository.maxId()+1;
        }
        bird.setBid(bid);
        birdRepository.save(bird.getBid(),bird.getChineseName(),bird.getProfessorName(),bird.getProtectGrade(),bird.getColor1(),bird.getFamily(),bird.getFamilyEnglish(),bird.getColor2(),
                bird.getOrderVice(),bird.getOrderViceEnglish(),bird.getColor3(),bird.getInformation(),bird.getExistInformation());
    }

    @Override
    public void delete(Long bid) {
        birdRepository.deleteById(bid);
    }

    @Override
    public void update(Bird bird) {
        birdRepository.save(bird);
    }

    @Override
    public Page<Bird> findPage(int pageNum, int pageSize) {
        List<Bird> birds= birdRepository.findPage(pageSize,pageNum*pageSize-pageSize);
        Page<Bird> birdPage=new Page<>();
        birdPage.setPageNum(pageNum);
        birdPage.setPageSize(pageSize);
        birdPage.setContent(birds);
        birdPage.setTotalElements(birdRepository.findCount());
        birdPage.setTotalPages((int)Math.ceil(birdPage.getTotalElements()/pageSize));
        return birdPage;
    }
}
