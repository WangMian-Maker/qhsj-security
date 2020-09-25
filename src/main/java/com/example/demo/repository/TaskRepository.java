package com.example.demo.repository;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

//    private Long tid;
//    private String createTime;
//    private String weather;
//    private String department;
//    private String leaders;
//    private String workers;
//    private String recode;
//    private String dealSuggest;
//    private String dealResult;
    @Modifying
    @Query(value = "insert into task (tid,create_time,weather,recode,deal_suggest,deal_result,status) values(?1,?2,?3,?4,?5,?6,?7)",nativeQuery = true)
    public void save(Long tid,String createTime,String weather,String recode,String dealSuggest,String dealResult,String status);

    @Query(value = "select max(t.tid) from Task t")
    public Long maxId();

    public Task findBytid(Long tid);
    //@Query(value = "update task set state='0' where tid=?1",nativeQuery = true)
    //public void deleteById(Long id);


    @Query(value = "select * from task where workers like ?3 order by tid desc limit ?1 offset ?2",nativeQuery = true)
    public List<Task> findPage(int pageSize,int startPoint,String workerLike);
    @Query(value = "select * from task order by tid desc limit ?1 offset ?2",nativeQuery = true)
    public List<Task> findPage(int pageSize,int startPoint);
    @Query(value = "select * from task where status=?1 and workers like ?2 order by tid desc limit ?3 offset ?4",nativeQuery = true)
    public List<Task> findBystatus(String status ,String workerLike,int pageSize,int startPoint);

    @Query(value = "select * from task where tid in (select task_tid from task_staff_workers where staff_workers_staff_id=?1)",nativeQuery = true)
    public List<Task> findByStaffId(Long StaffId);
    @Query(value = "select * from task where tid in (select task_tid from task_staff_workers where staff_workers_staff_id=?1) and status like ?2 order by tid desc limit ?3 offset ?4",nativeQuery = true)
    public List<Task> findingByStaffIdAndStatus(Long StaffId,String status,int pageSize,int startPoint);

    @Query(value = "select * from task where status like ?1 and tid in (select task_tid from task_staff_workers where staff_workers_staff_id=?2)",nativeQuery = true)
    public List<Task> findBystatus(String status,Long staffId);

    @Query(value = "select count(tid) from task",nativeQuery = true)
    public int findCount();

    @Query(value = "select count(tid) from task where tid in (select task_tid from task_staff_workers where staff_workers_staff_id=?1) and status like ?2",nativeQuery = true)
    public int findingCountByStaffIdAndStatus(Long StaffId,String status);
}
