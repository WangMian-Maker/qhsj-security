package com.example.demo.repository.Amphibious;

import com.example.demo.entity.biology.Amphibious.Amphibious;
import com.example.demo.entity.biology.Bird.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmphibiousRepository extends JpaRepository<Amphibious,Long> {
    @Query(value = "select a.orderVice from Amphibious a group by a.orderVice order by min(a.aid)")
    public List<String> findAllOrders();
    //@Query(value = "select distinct b.family from Bird b where b.orderVice=?1")
    @Query(value = "select a.family from Amphibious a where a.orderVice=?1 group by a.family order by min(aid)")
    public List<String> findAllFamilies(String orderVice);
    @Query(value = "select a.chineseName from Amphibious a where a.family=?1")
    public List<String> findAllChineseName(String family);

    public Amphibious findBychineseName(String chineseName);

    public List<Amphibious> findByorderVice(String orderVice);
    public List<Amphibious> findByfamily(String family);
}
