package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PDF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String time;
    private String path;

    private String describe;
    private String keyWord;

    @OneToOne
    private StaffInfor creator;
}
