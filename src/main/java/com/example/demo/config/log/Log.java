package com.example.demo.config.log;

import org.jboss.logging.BasicLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class Log {
    public final  static Logger logger= LoggerFactory.getLogger(BasicLogger.class);
}
