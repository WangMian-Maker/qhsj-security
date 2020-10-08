package com.example.demo.entity.biology.Bird;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class BiologyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long bid;

    public String planName;
    public String chineseName;
    public String latinName;
    public String biologyCount;
    public String watchCount;
    public String lindex;//使用顿号隔开I
    public String pindex;//使用顿号隔开
    public String other;
    private String biologyType;

    @ManyToOne
    public Biology biology;
}
