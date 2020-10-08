package com.example.demo.repository.Bird;

import com.example.demo.entity.biology.Bird.BiologyPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiologyPlanRepository extends JpaRepository<BiologyPlan,Long> {
    public List<BiologyPlan> findByplanName(String planName);
    public BiologyPlan findBychineseName(String chineseName);

    @Query(value = "select b from BiologyPlan b where b.chineseName=?1 and b.planName=?2")
    public BiologyPlan findByChineseNameAndPlanName(String chineseName, String planName);
//    public Long bid;
//
//    public String planName;
//    public String chineseName;
//    public String latinName;
//    public String biologyCount;
//    public String watchCount;
//    public String lIndex;//使用顿号隔开I
//    public String pIndex;//使用顿号隔开
//    public String other;
//
//    @ManyToOne
//    public biology biology;
    @Modifying
    @Query(value = "insert into biology_plan (bid,biology_count,watch_count,lindex,pindex,other,biology_bid,biology_type,plan_name) values (?1,?2,?3," +
            "?4,?5,?6,?7,?8,?9)",nativeQuery = true)
    public void save(Long bid,String biologyCount,String watchCount,String lIndex,
                     String pIndex,String other,Long biologyBid,String biologyType,String planName);

    @Query(value = "select * from biology_plan where biology_type=?3 order by bid desc limit ?1 offset ?2 ",nativeQuery = true)
    public List<BiologyPlan> findPage(int pageSize, int startPoint,String biologyType);

    @Query(value = "select count(bid) from biology_plan where biology_type=?1 ",nativeQuery = true)
    public int findCount(String biologyType);

    @Query(value = "select max(b.bid) from BiologyPlan b")
    public Long maxId();

    @Query(value = "select * from biology_plan where biology_bid=?1",nativeQuery = true)
    public BiologyPlan findByBiology(Long bid);

    public BiologyPlan findBybid(Long bid);
}
