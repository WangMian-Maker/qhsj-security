package com.example.demo.service.impl;

import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.repository.Bird.BirdRepository;
import com.example.demo.service.BirdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
}
