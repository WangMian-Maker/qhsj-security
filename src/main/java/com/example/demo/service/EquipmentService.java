package com.example.demo.service;

import com.example.demo.entity.Equipment;
import com.example.demo.entity.params.Page;

public interface EquipmentService {
    public void save(Equipment equipment);
    public void deleteById(Long id);
    public void update(Equipment equipment);
    public Page<Equipment> findPage(String status, String type, int pageNum, int pageSize);
}
