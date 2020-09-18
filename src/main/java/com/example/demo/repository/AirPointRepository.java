package com.example.demo.repository;

import com.example.demo.entity.AirPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirPointRepository extends JpaRepository<AirPoint,Long> {

    @Modifying
    @Query(value = "insert into air_point (aid,point,jing_wei_du,status,equip_index,example_index,img_path,camera_path) values (?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    public void save(Long aid,String point ,String jingWeiDu,String status,String equipIndex,String exampleIndex,String imgPath,String cameraPath);


    @Query(value = "select max(a.aid) from AirPoint a")
    public Long maxId();


    @Query(value = "select a from AirPoint a order by a.aid")
    public List<AirPoint> findAllByOrder();
}
