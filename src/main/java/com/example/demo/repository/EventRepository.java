package com.example.demo.repository;

import com.example.demo.entity.Department;
import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ManyToOne;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    //private Long eid;
//private String eventIndex;
//private String eventGrade;
//private String department;
//private String position;
//private String chargePerson;
//private String photoPath;
//private String videoPath;
//private String status;
//private String eventSource;
//private String findTime;
//private String information;
//private String findPerson;
//private String dealPerson;
//private String dealTime;
//private String dealResult;
//private String operationPerson;
//private String blackList;
//private String influence;
//    @ManyToOne
//    private Department department;
//    @ManyToOne
//    private StaffInfor findPerson;
//    @ManyToOne
//    private StaffInfor dealPerson;
//    @ManyToOne
//    private StaffInfor operationPerson;
    @Query(value = "select max(e.eid) from Event e")
    public Long maxId();
    public Event findByeid(Long eid);
    @Modifying
    @Query(value = "insert into event(eid,event_index,event_type,event_grade,department_did,position,charge_person,photo_path,video_path,status,event_source,find_time,information" +
            ",find_person_staff_id,deal_person_staff_id,deal_time,deal_result,operation_person_staff_id,black_list,influence,task_tid) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16,?17,?18,?19,?20,?21)",nativeQuery = true)
    public void save(Long eid,String eventIndex,String eventType, String eventGrade,Long departmentDid,String position,String chargePerson,String photoPath,
                     String videoPath,String status,String eventSource,String findTime,String information,Long findPersonStaffId,Long dealPersonStaffId,
                     String dealTime,String dealResult,Long operationPersonStaffId,String blackList,String influence,Long taskTid);

    @Modifying
    @Query(value = "insert into event(eid,event_index,event_type,event_grade,department,position,charge_person,photo_path,video_path,status,event_source,find_time,information" +
            ",find_person,deal_person,deal_time,deal_result,operation_person,black_list,influence) values(?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14,?15,?16)",nativeQuery = true)
    public void save(Long eid,String eventIndex,String eventType, String eventGrade,String position,String chargePerson,String photoPath,
                     String videoPath,String status,String eventSource,String findTime,String information,
                     String dealTime,String dealResult,String blackList,String influence);
    @Query(value = "select e from Event e where e.task.tid=?1")
    public List<Event> findEventInTask(Long tid);

    @Query(value = "select * from event where task_tid=?1 and status like ?2",nativeQuery = true)
    public List<Event> findByTaskAndStatus(Long tid,String status);

    @Query(value = "select * from event where find_person_staff_id=?1 and status like ?2",nativeQuery = true)
    public List<Event> findByFindPersonAndStatus(Long staffId,String status);
}
