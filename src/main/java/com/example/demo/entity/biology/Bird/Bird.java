package com.example.demo.entity.biology.Bird;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Bird {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long bid;
    public String chineseName;
    public String professorName;
    public String color1;

    public String family;//科
    public String familyEnglish;
    public String color2;
    public String orderVice;// 目
    public String orderViceEnglish;
    public String color3;
}
