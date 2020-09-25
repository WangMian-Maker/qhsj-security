package com.example.demo.repository.Bird;

import com.example.demo.entity.biology.Bird.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query(value = "select max(b.bid) from Bird b")
    public Long maxId();

//    public Long bid;
//    public String chineseName;
//    public String professorName;
//    public String color1;
//
//    public String family;//科
//    public String familyEnglish;
//    public String color2;
//    public String orderVice;// 目
//    public String orderViceEnglish;
//    public String color3;

    @Modifying
    @Query(value = "insert into bird (bid,chinese_name,professor_name,protect_grade,color1,family,family_english,color2,order_vice,order_vice_english,color3,information,exist_information) values (?1,?2,?3," +
            "?4,?5,?6,?7,?8,?9,?10,?11,?12,?13)",nativeQuery = true)
    public void save(Long bid,String chineseName,String professorName,String protectGrade,String color1,String family,String familyEnglish,String color2,
                     String orderVice,String orderViceEnglish,String color3,String information,String existInformation);

    @Query(value = "select * from bird order by bid desc limit ?1 offset ?2 ",nativeQuery = true)
    public List<Bird> findPage(int pageSize,int startPoint);

    @Query(value = "select count(bid) from bird ",nativeQuery = true)
    public int findCount();
}
