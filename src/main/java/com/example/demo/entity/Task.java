package com.example.demo.entity;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.example.demo.entity.events.Event;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.LineString;
import lombok.Data;

import javax.persistence.*;
import javax.sound.sampled.Line;
import java.awt.*;
import java.awt.geom.Line2D;
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

    @JsonSerialize(using= com.example.demo.entity.events.GeometrySerializer.class)
    @JsonDeserialize(using = com.example.demo.entity.events.GeometryDeserializer.class)
    private LineString originLine;

    @JsonSerialize(using= com.example.demo.entity.events.GeometrySerializer.class)
    @JsonDeserialize(using = com.example.demo.entity.events.GeometryDeserializer.class)
    private LineString realLine;
}
