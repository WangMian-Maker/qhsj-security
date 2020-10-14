package com.example.demo.repository;

import com.example.demo.entity.ProtectGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface ProtectGradeRepository extends JpaRepository<ProtectGrade,Long> {

    String findByGradeSql="select protect_grade from protect_grade where id=?1";
    String findByGradeIntSql="select grade from protect_grade where id=?1";
    @Query(value = findByGradeSql,nativeQuery = true)
    public String findByGradeSql(Long grade);

    @Query(value = findByGradeIntSql,nativeQuery = true)
    public Integer findByGradeInt(Long grade);
}
