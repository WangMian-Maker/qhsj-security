package com.example.demo.repository.user;

import com.example.demo.entity.user.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleRepository extends JpaRepository<SysRole,Long> {

    @Query(value = "select s from SysRole s order by s.sid")
    public List<SysRole> findAllOrderBySid();

    public SysRole findByroleName(String roleName);

    @Query(value = "select s.sid from SysRole s order by s.sid")
    public List<Long> findAllSid();

    @Query(value = "select max(s.sid) from SysRole s")
    public Long maxId();

    public SysRole findBysid(Long sid);

    @Query(value = "select sys_user_sid from sys_user_roles where roles_sid=?1",nativeQuery = true)
    public List<Long> findRoleInUser(Long roleId);
}
