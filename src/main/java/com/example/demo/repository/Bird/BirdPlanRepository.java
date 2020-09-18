package com.example.demo.repository.Bird;

import com.example.demo.entity.biology.Bird.BirdPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirdPlanRepository extends JpaRepository<BirdPlan,Long> {
    public List<BirdPlan> findByplanName(String planName);
    public BirdPlan findBychineseName(String chineseName);

    @Query(value = "select b from BirdPlan b where b.chineseName=?1 and b.planName=?2")
    public BirdPlan findByChineseNameAndPlanName(String chineseName,String planName);
}
