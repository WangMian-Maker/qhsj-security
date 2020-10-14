package com.example.demo.entity;

import com.example.demo.entity.events.GeometryDeserializer;
import com.example.demo.entity.events.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Point point;
    public String status;
    public String index;

    @OneToOne
    private StaffInfor chargePerson;

    private String phoneNumber;

    private String type;
}
