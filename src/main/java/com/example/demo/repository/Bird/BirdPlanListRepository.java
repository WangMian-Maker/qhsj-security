package com.example.demo.repository.Bird;

import com.example.demo.entity.StaffInfor;
import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.biology.Bird.BirdPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.biology.Bird.BirdPlanList;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;


@Repository
public interface BirdPlanListRepository extends JpaRepository<BirdPlanList,Long> {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long bid;
//    private String time;
//    private String equipment;
//    private String index;
//
//    @ManyToMany
//    private List<StaffInfor> workers;
//
//    @ManyToMany
//    private List<Bird> birds;
//
//    @OneToMany
//    private List<BirdPlan> birdPlans;
    @Modifying
    @Query(value = "insert into bird_plan_list (bid,time,equipment,index) values(?1,?2,?3,?4)",nativeQuery = true)
    public void save(Long bid, String time,String equipment,String index);

    @Query(value = "select max(bid) from bird_plan_list",nativeQuery = true)
    public Long maxId();

    public BirdPlanList findBybid(Long bid);

    @Query(value = "select * from bird_plan_list order by bid desc limit ?1 offset ?2",nativeQuery = true)
    public List<BirdPlanList> findPage(int pageSize,int startPoint);

    @Query(value = "select bid from bird_plan where bid in (select bird_plans_bid from bird_plan_list_bird_plans where bird_plan_list_bid=?3) order by bid desc limit ?1 offset ?2",nativeQuery = true)
    public List<Long> findPageForBirdPlan(int pageSize,int startPoint,Long bid);

}
