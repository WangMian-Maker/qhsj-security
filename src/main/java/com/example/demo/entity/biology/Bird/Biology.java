package com.example.demo.entity.biology.Bird;


import lombok.Data;

import javax.persistence.*;
@Entity
@Data
public class Biology {
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
    @Column(length = 1000)
    private String information;
    @Column(length = 1000)
    private String existInformation;
    private String biologyType;
    private String img_scr;
}
