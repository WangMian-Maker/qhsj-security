package com.example.demo.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class SysRights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;

    private String rightsName;

    private String url;

    private String menuUrl;

    private String m_order;

    @OneToMany(fetch = FetchType.EAGER)
    public List<SysRights> children;
}
