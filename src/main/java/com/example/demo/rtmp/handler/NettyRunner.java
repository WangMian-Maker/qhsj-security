package com.example.demo.rtmp.handler;

import com.example.demo.NetConfig;
import com.example.demo.rtmp.server.RTMPServer;
import com.example.demo.rtmp.server.entities.Stream;
import com.example.demo.rtmp.server.entities.StreamName;
import com.example.demo.rtmp.server.HttpFlvServer;
import com.example.demo.rtmp.server.manager.StreamManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(0)
public class NettyRunner implements ApplicationRunner {

    @Autowired
    private Environment environment;
    public static StreamManager streamManager;
    @Override
    public void run(ApplicationArguments args) throws Exception {

            NetConfig.setSaveFlvFile(environment.getProperty("saveFlvFile",Boolean.class));
            NetConfig.setSaveFlVFilePath(environment.getProperty("saveFlVFilePath",String.class));
            streamManager = new StreamManager();

            int rtmpPort = environment.getProperty("rtmpPort",Integer.class);
            int handlerThreadPoolSize=environment.getProperty("handlerThreadPoolSize",Integer.class);
            System.out.println(rtmpPort);
            System.out.println(handlerThreadPoolSize);
            RTMPServer rtmpServer = new RTMPServer(rtmpPort, streamManager,handlerThreadPoolSize);
            rtmpServer.run();

            if (!environment.getProperty("enableHttpFlv",Boolean.class)) {
                return;
            }

            int httpPort =environment.getProperty("httpPort",Integer.class);
            HttpFlvServer httpFlvServer = new HttpFlvServer(httpPort, streamManager,handlerThreadPoolSize);
            httpFlvServer.run();

    }

    private void createStream(ChannelHandlerContext ctx, StreamName streamName) {
        Stream s = new Stream(streamName);
        s.setPublisher(ctx.channel());
        streamManager.newStream(streamName, s);
    }

}
