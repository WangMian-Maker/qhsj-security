package com.example.demo.entity.user;

import com.example.demo.entity.StaffInfor;
import lombok.Data;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.List;

@Entity
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;
    private String account;
    public String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<SysRole> roles;

    @ManyToOne
    private StaffInfor staffInfor;

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public StaffInfor getStaffInfor() {
        return staffInfor;
    }

    public void setStaffInfor(StaffInfor staffInfor) {
        this.staffInfor = staffInfor;
    }

}
