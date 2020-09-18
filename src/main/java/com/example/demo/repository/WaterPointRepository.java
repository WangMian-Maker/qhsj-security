package com.example.demo.repository;

import com.example.demo.entity.AirPoint;
import com.example.demo.entity.WaterPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterPointRepository extends JpaRepository<WaterPoint,Long> {
    @Modifying
    @Query(value = "insert into air_point (wid,point,jing_wei_du,status,equip_index,example_index,img_path,camera_path) values (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    public void save(Long wid,String point ,String jingWeiDu,String status,String equipIndex,String exampleIndex,String imgPath,String cameraPath);


    @Query(value = "select max(w.wid) from WaterPoint w")
    public Long maxId();


    @Query(value = "select w from WaterPoint w order by w.wid")
    public List<WaterPoint> findAllByOrder();
}
