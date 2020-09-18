package com.example.demo.entity.biology.Bird;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BirdPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long bid;

    public String planName;
    public String chineseName;
    public String latinName;
    public String birdCount;
    public String watchCount;
    public String lIndex;//使用顿号隔开I
    public String pIndex;//使用顿号隔开
    public String other;
}
