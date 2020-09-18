package com.example.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class AirData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long aid;
    public String time;
    public String aqi;
    public String pm25;
    public String pm10;
    public String so2;
    public String no2;
    public String o3;
    public String co;
    public String point;

}
