package com.example.demo.entity.biology.Bird;


import lombok.Data;

import javax.persistence.*;
@Entity
@Data
public class Bird {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long bid;
    public String chineseName;
    public String professorName;
    public String color1;
    private String protectGrade;
    public String family;//科
    public String familyEnglish;
    public String color2;
    public String orderVice;// 目
    public String orderViceEnglish;
    public String color3;
    private String information;
    private String existInformation;
}
