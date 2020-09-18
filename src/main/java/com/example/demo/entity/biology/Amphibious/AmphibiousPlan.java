package com.example.demo.entity.biology.Amphibious;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class AmphibiousPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long aid;

    public String planName;
    public String chineseName;
    public String latinName;
    public String amphibiousCount;
    public String watchCount;
    public String lIndex;//使用顿号隔开I
    public String pIndex;//使用顿号隔开
    public String other;
}
