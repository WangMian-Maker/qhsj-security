package com.example.demo.entity;

import com.example.demo.entity.user.SysUser;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class StaffInfor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;
    private String staffName;
    private String sex;
    private String birthday;
    private String nature;
    private String outlook;//政治面貌
    //private String department;
    private String position;
    private String beginWorkTime;
    private String idCard;
    private String phoneNumber;
    private String mail;
    private String marriage;
    private String birthPosition;
    private String currentPosition;
    private String censusRegister;

    @OneToOne
    private Department department;
}
