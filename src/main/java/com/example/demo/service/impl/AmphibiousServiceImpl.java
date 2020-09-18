package com.example.demo.service.impl;


import com.example.demo.entity.biology.Amphibious.Amphibious;
import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.repository.Amphibious.AmphibiousRepository;
import com.example.demo.repository.Bird.BirdRepository;
import com.example.demo.service.AmphibiousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmphibiousServiceImpl implements AmphibiousService {
    @Autowired
    private AmphibiousRepository amphibiousRepository;
    @Override
    public List<String> findAllOrders() {
        return amphibiousRepository.findAllOrders();
    }

    @Override
    public List<String> findAllFamiliesByOrder(String order) {
        return amphibiousRepository.findAllFamilies(order);
    }

    @Override
    public List<String> findAllChineseNameByFamily(String family) {
        return amphibiousRepository.findAllChineseName(family);
    }

    @Override
    public List<Amphibious> findAll() {
        return amphibiousRepository.findAll();
    }
}
