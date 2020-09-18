package com.example.demo.repository.Amphibious;

import com.example.demo.entity.biology.Amphibious.AmphibiousPlan;
import com.example.demo.entity.biology.Bird.BirdPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AmphibiousPlanRepository extends JpaRepository<AmphibiousPlan,Long> {
    public List<AmphibiousPlan> findByplanName(String planName);
    public AmphibiousPlan findBychineseName(String chineseName);

    @Query(value = "select a from AmphibiousPlan a where a.chineseName=?1 and a.planName=?2")
    public AmphibiousPlan findByChineseNameAndPlanName(String chineseName,String planName);
}
