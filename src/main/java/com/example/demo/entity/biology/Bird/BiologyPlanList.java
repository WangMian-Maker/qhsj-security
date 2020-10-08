package com.example.demo.entity.biology.Bird;

import com.example.demo.entity.StaffInfor;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class BiologyPlanList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private String time;
    private String equipment;
    private String index;
    private String biologyType;

    @ManyToMany
    private List<StaffInfor> workers;

    @ManyToMany
    private List<Biology> biologies;

    @OneToMany
    private List<BiologyPlan> biologyPlans;
}
