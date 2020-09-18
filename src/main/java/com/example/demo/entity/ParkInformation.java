package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class ParkInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long pid;

    public String parkSize;

    public String waterSize;
    public String waterPer;

    @OneToMany
    public Set<AreaInfor> areaType;

    @OneToMany
    public Set<AreaInfor> functionType;
}
