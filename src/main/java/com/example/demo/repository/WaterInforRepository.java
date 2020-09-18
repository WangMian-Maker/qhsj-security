package com.example.demo.repository;

import com.example.demo.entity.WaterInfor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterInforRepository extends JpaRepository<WaterInfor,Long>, JpaSpecificationExecutor<WaterInfor> {
    @Query(value = "select distinct w.point from WaterInfor w")
    public List<String> findAllPoint();

    @Query(value = "select max(w.wid) from WaterInfor w")
    public Long maxId();
}
