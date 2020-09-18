package com.example.demo.repository.user;

import com.example.demo.entity.user.SysRights;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRightsRepository extends JpaRepository<SysRights,Long> {
    @Query(value = "select s.sid from SysRights s order by s.sid")
    public List<Long> findAllSid();

    @Query(value = "select max(s.sid) from SysRights s")
    public Long maxId();

    public SysRights findBysid(Long sid);

    @Query(value = "select s from SysRights s where s.m_order like 'r%'")
    public List<SysRights> findAllMenu();
}
