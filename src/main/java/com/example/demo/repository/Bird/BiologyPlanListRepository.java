package com.example.demo.repository.Bird;

import com.example.demo.entity.biology.Bird.BiologyPlanList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BiologyPlanListRepository extends JpaRepository<BiologyPlanList,Long> {
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
//    private List<biology> biologys;
//
//    @OneToMany
//    private List<biologyPlan> biologyPlans;
    @Modifying
    @Query(value = "insert into biology_plan_list (bid,time,equipment,index,biology_type) values(?1,?2,?3,?4,?5)",nativeQuery = true)
    public void save(Long bid, String time,String equipment,String index,String biologyType);

    @Query(value = "select max(bid) from biology_plan_list",nativeQuery = true)
    public Long maxId();

    public BiologyPlanList findBybid(Long bid);

    @Query(value = "select * from biology_plan_list where biology_type=?3 order by bid desc limit ?1 offset ?2",nativeQuery = true)
    public List<BiologyPlanList> findPage(int pageSize, int startPoint,String biologyType);

    @Query(value = "select bid from biology_plan where biology_type=?4 and bid in (select biology_plans_bid from biology_plan_list_biology_plans where biology_plan_list_bid=?3) order by bid desc limit ?1 offset ?2",nativeQuery = true)
    public List<Long> findPageForbiologyPlan(int pageSize,int startPoint,Long bid,String biologyType);


    @Query(value = "select * from biology_plan_list  where bid in (select biology_plan_list_bid from biology_plan_list_biologies where biologies_bid=?1)",nativeQuery = true)
    public BiologyPlanList findByBiology(Long bid);

    @Query(value = "select * from biology_plan_list  where bid in (select biology_plan_list_bid from biology_plan_list_biology_plans where biology_plans_bid=?1)",nativeQuery = true)
    public BiologyPlanList findByBiologyPlan(Long bid);

    @Query(value = "select * from biology_plan_list where biology_type=?1",nativeQuery = true)
    public List<BiologyPlanList> findAll(String biologyType);
}
