package com.example.demo.entity.events;

import com.example.demo.entity.Department;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.Task;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.awt.PointShapeFactory;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Time;

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

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Point point;
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
    private Long time;

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
