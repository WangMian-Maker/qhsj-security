package com.example.demo.repository.Bird;

import com.example.demo.entity.biology.Bird.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BirdRepository extends JpaRepository<Bird,Long> {


    //@Query(value = "select distinct b.orderVice from Bird b")
    @Query(value = "select b.orderVice from Bird b group by b.orderVice order by min(b.bid)")
    public List<String> findAllOrders();
    //@Query(value = "select distinct b.family from Bird b where b.orderVice=?1")
    @Query(value = "select b.family from Bird b where b.orderVice=?1 group by b.family order by min(bid)")
    public List<String> findAllFamilies(String orderVice);
    @Query(value = "select b.chineseName from Bird b where b.family=?1")
    public List<String> findAllChineseName(String family);

    public Bird findBychineseName(String chineseName);

    public List<Bird> findByorderVice(String orderVice);
    public List<Bird> findByfamily(String family);
}
