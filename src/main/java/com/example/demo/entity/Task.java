package com.example.demo.entity;

import com.example.demo.entity.events.Event;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "task_tid")
    private Long tid;
    //private String creator;
    private String createTime;
    private String weather;
    //private String department;
    //private String leaders;
    //private String workers;
    private String recode;
    private String dealSuggest;
    private String dealResult;
    private String status;
    private Long time;

    @OneToOne
    private Department department;
    @ManyToOne
    private StaffInfor creator;
    @ManyToMany
    private List<StaffInfor> leaders;
    @ManyToMany
    private List<StaffInfor> staffWorkers;
}
