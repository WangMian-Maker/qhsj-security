package com.example.demo.repository;

import com.example.demo.entity.Equipment;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.events.GeometrySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vividsolutions.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.OneToOne;
import java.util.List;


@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
//    private Long id;
//    private String name;
//    @JsonSerialize(using = GeometrySerializer.class)
//    //@JsonDeserialize(using = GeometryDeserializer.class)
//    private Point point;
//    public String status;
//    public String index;
//
//    @OneToOne
//    private StaffInfor chargePerson;
//
//    private String phoneNumber;
//
//    private String type;

    String findPage="select * from equipment where status like ?1 and type like ?2 order by id desc limit ?3 offset ?4";
    String findMaxId="select max(id) from equipment";
    String save="insert into equipment (id,name,point,status,index,charge_person_staff_id,phone_number,type) values(?1,?2,?3,?4,?5,?6,?7,?8)";

    @Query(value = save, nativeQuery = true)
    @Modifying
    public void save(Long id, String name, Point point, String status, String index, Long chargePersonStaffId, String phoneNumber, String type);


    @Query(value = findPage,nativeQuery = true)
    public List<Equipment> findPage(String statusLike,String typeLike,int pageSize,int startPoint);

    @Query(value = findMaxId,nativeQuery = true)
    public Long findMaxId();
}
