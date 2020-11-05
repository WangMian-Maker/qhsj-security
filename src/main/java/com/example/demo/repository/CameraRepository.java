package com.example.demo.repository;


import com.example.demo.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Repository
public interface CameraRepository extends JpaRepository<Camera,Long> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String ip;
//    private int port;
//    private String originPos;//初始位置角度
//    private int wayNum;//通道号
//    private boolean isInMachine;//是否接入了录像机
//    private String account;
//    private String password;
//    private String rtspUrl;
    String saveSql="insert into camera (id,ip,port,origin_pos,way_num,is_in_machine,account,password,rtsp_url,camera_name,rtmp_url,http_url) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12)";
    String findMaxId="select max(id) from camera";
    String findPageSql="select * from camera order by id limit ?1 offset ?2";
    @Modifying
    @Query(value = saveSql,nativeQuery = true)
    public void save(Long id,String ip,int port,String originPos,int wayNum ,Boolean isInMachine,String account,String password,String rtspUrl,String cameraName,String rtmpUrl,String httpUrl);

    @Query(value = findMaxId,nativeQuery = true)
    public Long findMaxId();

    @Query(value = findPageSql,nativeQuery = true)
    public List<Camera> findPage(int pageSize,int startPoint);
}
