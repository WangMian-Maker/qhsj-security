package com.example.demo.repository.user;

import com.example.demo.entity.user.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    @Query(value = "select s.sid from SysUser s order by s.sid")
    public List<Long> findAllSid();

    @Modifying
    @Query(value = "update sys_user set password=?2 where sid=?1",nativeQuery = true)
    public void save(Long sid,String password);

    @Query(value = "select max(s.sid) from SysUser s")
    public Long maxId();

    public SysUser findByaccount(String account);

    @Query(value = "select s from SysUser s where s.sid=?1")
    public SysUser findBysid(Long sid);

    @Query(value = "select s from SysUser s where s.staffInfor.staffId=?1")
    public SysUser findByStaffId(Long staffId);
}
