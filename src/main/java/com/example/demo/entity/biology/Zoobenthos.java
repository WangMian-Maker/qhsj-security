package com.example.demo.entity.biology;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Zoobenthos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long zid;
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
