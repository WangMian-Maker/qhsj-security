package com.example.demo.entity.events;

import com.example.demo.entity.Department;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.Task;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eid;
    private String eventIndex;
    private String eventType;
    private String eventGrade;
    //private String department;
    private String position;
    //private String chargePerson;
    private String photoPath;
    private String videoPath;
    private String status;
    private String eventSource;
    private String findTime;
    private String information;
    //private String findPerson;
    //private String dealPerson;
    private String dealTime;
    private String dealResult;
    //private String operationPerson;
    private String blackList;
    private String influence;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Department department;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private StaffInfor chargePerson;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private StaffInfor findPerson;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private StaffInfor dealPerson;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private StaffInfor operationPerson;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Task task;
}
