package com.example.demo.service.impl;

import com.example.demo.entity.Camera;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.CameraRepository;
import com.example.demo.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CameraServiceImpl implements CameraService {
    @Autowired
    private CameraRepository cameraRepository;

    @Override
    public void save(Camera camera) {
        Long id=1L;
        if(cameraRepository.findMaxId()!=null){
            id=cameraRepository.findMaxId()+1;
        }
        camera.setId(id);
        cameraRepository.save(camera.getId(),camera.getIp(),camera.getPort(),camera.getOriginPos(),camera.getWayNum(),camera.getIsInMachine(),camera.getAccount(),camera.getPassword(),camera.getRtspUrl());
    }

    @Override
    public void deleteById(Long id) {
        cameraRepository.deleteById(id);
    }

    @Override
    public void update(Camera camera) {
        cameraRepository.save(camera);
    }

    @Override
    public Page<Camera> findPage(int pageNum, int pageSize) {
        Page<Camera> cameraPage=new Page<>();
        int startPoint=pageNum*pageSize-pageSize;
        cameraPage.setContent(cameraRepository.findPage(pageSize,startPoint));
        cameraPage.setTotalElements(cameraRepository.findAll()==null?0:cameraRepository.findAll().size());
        cameraPage.setPageSize(pageSize);
        cameraPage.setPageNum(pageNum);
        cameraPage.setTotalElements((int)Math.ceil((float)cameraPage.getTotalElements()/(float)cameraPage.getPageSize()));
        return cameraPage;
    }
}
