package com.example.demo.repository.Bird;

import com.example.demo.entity.biology.Bird.Bird;
import com.example.demo.entity.biology.Bird.BirdPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.ManyToOne;
import java.util.List;

@Repository
public interface BirdPlanRepository extends JpaRepository<BirdPlan,Long> {
    public List<BirdPlan> findByplanName(String planName);
    public BirdPlan findBychineseName(String chineseName);

    @Query(value = "select b from BirdPlan b where b.chineseName=?1 and b.planName=?2")
    public BirdPlan findByChineseNameAndPlanName(String chineseName,String planName);
//    public Long bid;
//
//    public String planName;
//    public String chineseName;
//    public String latinName;
//    public String birdCount;
//    public String watchCount;
//    public String lIndex;//使用顿号隔开I
//    public String pIndex;//使用顿号隔开
//    public String other;
//
//    @ManyToOne
//    public Bird bird;
    @Modifying
    @Query(value = "insert into bird_plan (bid,plan_name,chinese_name,latin_name,bird_count,watch_count,lindex,pindex,other,bird_bid) values (?1,?2,?3," +
            "?4,?5,?6,?7,?8,?9,?10)",nativeQuery = true)
    public void save(Long bid,String planName,String chineseName,String latinName,String birdCount,String watchCount,String lIndex,
                     String pIndex,String other,Long bird_bid);

    @Query(value = "select * from bird_plan order by bid desc limit ?1 offset ?2 ",nativeQuery = true)
    public List<BirdPlan> findPage(int pageSize, int startPoint);

    @Query(value = "select count(bid) from bird_plan ",nativeQuery = true)
    public int findCount();

    @Query(value = "select max(b.bid) from BirdPlan b")
    public Long maxId();

    public BirdPlan findBybid(Long bid);
}
