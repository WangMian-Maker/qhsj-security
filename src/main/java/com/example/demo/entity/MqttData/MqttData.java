package com.example.demo.entity.MqttData;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class MqttData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;
    private String temperature;
    private String humidity;
    private String pm25;
    private String pm10;
    private String pressure;
    private String windSpeed;
    private String windDirection;
    private String noise;
    private String rainfall;
    private String earth;
    private String evaporation;
    private String so2;
    private String no2;
    private String o3;
    private String co;
}
