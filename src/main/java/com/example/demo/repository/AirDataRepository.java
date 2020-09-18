package com.example.demo.repository;

import com.example.demo.entity.AirData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//public Long aid;
//public String time;
//public String aqi;
//public String pm25;
//public String pm10;
//public String so2;
//public String no2;
//public String o3;
//public String co;
//public String point;
@Repository
public interface AirDataRepository extends JpaRepository<AirData,Long>, JpaSpecificationExecutor<AirData> {
    public AirData findBypoint(String point);

    @Query(value = "select max(a.aid) from AirData a")
    public Long findId();

    @Modifying
    @Query(value = "insert into air_data (aid,time,aqi,pm25,pm10,so2,no2,o3,co,point) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10)",nativeQuery = true)
    public void save(Long aid,String time,String aqi,String pm25,String pm10,String so2,String no2,String o3,String co,String point);
}
