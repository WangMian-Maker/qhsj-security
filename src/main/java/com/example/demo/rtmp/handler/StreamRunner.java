package com.example.demo.rtmp.handler;

import com.example.demo.NetConfig;
import com.example.demo.entity.Camera;
import com.example.demo.service.impl.CameraServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Order(1)
public class StreamRunner implements ApplicationRunner, EnvironmentAware {
    @Autowired
    private Environment environment;

    @Autowired
    private  StreamService streamService;

    @Autowired
    private  CameraServiceImpl cameraService;

    private Binder binder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            List<Camera> cameras=cameraService.findAll();
            for(Camera camera:cameras){
                String streamUrl=camera.getRtspUrl();
                //String rtmpUrl="rtmp://192.168.0.212:9091/live/"+camera.getId();
                String rtmpUrl="rtmp://"+ NetConfig.getIpAddress()+":"+environment.getProperty("rtmpPort",Integer.class)+"/live/"+camera.getId();
                camera.setHttpUrl(rtmpUrl.replace("rtmp://"+ NetConfig.getIpAddress()+":"+environment.getProperty("rtmpPort",Integer.class),
                        "http://"+ NetConfig.getIpAddress()+":"+environment.getProperty("httpPort",Integer.class)));
                cameraService.update(camera);
                streamService.play(streamUrl,rtmpUrl);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void setEnvironment(Environment environment) {
        binder = Binder.get(environment);
    }
}
