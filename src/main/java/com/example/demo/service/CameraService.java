package com.example.demo.service;

import com.example.demo.entity.Camera;
import com.example.demo.entity.params.Page;

public interface CameraService {
    public void save(Camera camera);
    public void deleteById(Long id);
    public void update(Camera camera);
    public Page<Camera> findPage(int pageNum, int pageSize);
}
