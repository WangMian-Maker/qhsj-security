package com.example.demo.entity;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class WaterInfor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long wid;

    public String time;
    public String temprature;
    public String transparency;//tou ming du
    public String ph;
    public String do_;
    public String pi;// gao meng suan yan
    public String cod;
    public String bod;
    public String nh3_n;
    public String n;
    public String p;
    public String vp;
    public String cyanide;
    public String arsenic;
    public String hc;
    public String hg;
    public String pb;
    public String cd;
    public String cu;
    public String zn;
    public String se;
    public String f_;
    public String s_;
    public String c_a;
    public String petroleum;
    public String as_;
    public String fc;
    public String conductivity;
    public String waterQuality;
    public String nutritionalStatus;
    public String way;
    public String point;

}
