package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableSwagger2
@EnableAsync
@Slf4j
public class DemoApplication extends SpringBootServletInitializer {
	public static void main(String[] args) throws UnknownHostException {

		SpringApplication.run(DemoApplication.class, args);

	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(com.example.demo.DemoApplication.class);


	}
}
