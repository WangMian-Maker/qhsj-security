package com.example.demo.entity.user;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    public List<SysRights> rights;
}
