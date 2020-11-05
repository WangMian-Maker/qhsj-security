package com.example.demo.service.impl;

import com.example.demo.NetConfig;
import com.example.demo.entity.Camera;
import com.example.demo.entity.params.Page;
import com.example.demo.repository.CameraRepository;
import com.example.demo.rtmp.handler.NettyRunner;
import com.example.demo.rtmp.handler.StreamService;
import com.example.demo.rtmp.server.entities.StreamName;
import com.example.demo.service.CameraService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CameraServiceImpl implements CameraService {
    @Autowired
    private CameraRepository cameraRepository;

    @Autowired
    private CameraServiceImpl cameraService;
    @Autowired
    private StreamService streamService;
    @Autowired
    private Environment environment;
    public void run(Camera camera) throws Exception {
        try {
            List<Camera> cameras=cameraService.findAll();
            String streamUrl=camera.getRtspUrl();
            String rtmpUrl="rtmp://"+ NetConfig.getIpAddress()+":"+environment.getProperty("rtmpPort",Integer.class)+"/live/"+camera.getId();
            camera.setHttpUrl(rtmpUrl.replace("rtmp://"+ NetConfig.getIpAddress()+":"+environment.getProperty("rtmpPort",Integer.class),
                    "http://"+ NetConfig.getIpAddress()+":"+environment.getProperty("httpPort",Integer.class)));
            cameraService.update(camera);
            streamService.play(streamUrl,rtmpUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void save(Camera camera) {
        Long id=1L;
        if(cameraRepository.findMaxId()!=null){
            id=cameraRepository.findMaxId()+1;
        }
        camera.setId(id);
        cameraRepository.save(camera.getId(),camera.getIp(),camera.getPort(),camera.getOriginPos(),camera.getWayNum(),camera.getIsInMachine(),camera.getAccount(),camera.getPassword(),camera.getRtspUrl(),camera.getCameraName(),camera.getRtmpUrl(),camera.getHttpUrl());
        try {
            run(camera);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(Long id) {
        cameraRepository.deleteById(id);
        StreamName streamName=new StreamName("live",id.toString());
        NettyRunner.streamManager.exist(streamName);
        NettyRunner.streamManager.remove(streamName);
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

    @Override
    public List<Camera> findAll() {
        return cameraRepository.findAll();
    }
}
