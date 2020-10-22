package com.example.demo.entity.biology;

import lombok.Data;

@Data
public class MainPageData {
    public String name;
    public int grade;
    public String url;
    public  MainPageData (String name,int grade,String url){
        this.name=name;
        this.grade=grade;
        this.url=url;
    }
}
