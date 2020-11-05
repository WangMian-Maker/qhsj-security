package com.example.demo.rtmp.handler;

import org.bytedeco.javacv.FrameGrabber;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StreamService {

    @Async
    public void play(String streamUrl,String rtmpUrl) throws IOException {

        new ConvertVideoPakcet().from(streamUrl)
                .to(rtmpUrl)
                .go();
    }
}
