package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Camera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ip;
    private int port;
    private String originPos;//初始位置角度
    private int wayNum;//通道号
    private Boolean isInMachine;//是否接入了录像机
    private String account;
    private String password;
    private String rtspUrl;
}
