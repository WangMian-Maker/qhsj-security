package com.example.demo.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class AirPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long aid;
    public String point;
    public String jingWeiDu;
    public String status;
    public String equipIndex;
    public String exampleIndex;
    public String imgPath;
    public String cameraPath;
}
