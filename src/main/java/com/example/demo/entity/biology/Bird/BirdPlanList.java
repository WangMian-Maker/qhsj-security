package com.example.demo.entity.biology.Bird;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.biology.Bird.BirdPlan;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class BirdPlanList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;
    private String time;
    private String equipment;
    private String index;

    @ManyToMany
    private List<StaffInfor> workers;

    @ManyToMany
    private List<Bird> birds;

    @OneToMany
    private List<BirdPlan> birdPlans;
}
