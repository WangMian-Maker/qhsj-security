package com.example.demo.repository.Bird;

import com.example.demo.entity.biology.Bird.Biology;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiologyRepository extends JpaRepository<Biology,Long> {


    //@Query(value = "select distinct b.orderVice from biology b")
    @Query(value = "select b.orderVice from Biology b where b.biologyType=?1 group by b.orderVice order by min(b.bid)")
    public List<String> findAllOrders(String biologyType);
    //@Query(value = "select distinct b.family from Biology b where b.orderVice=?1 and b.biologyType=?2")
    @Query(value = "select family from biology where order_vice=?1 and and biology_type=?2 group by family order by min(bid)",nativeQuery = true)
    public List<String> findAllFamilies(String orderVice,String biologyType);
    @Query(value = "select b.chineseName from Biology b where b.family=?1 and b.biologyType=?2")
    public List<String> findAllChineseName(String family,String biologyType);


    @Query(value = "select b from Biology b where b.chineseName=?1 and b.biologyType=?2")
    public Biology findBychineseName(String chineseName,String biologyType);
    @Query(value = "select b from Biology b where b.orderVice=?1 and b.biologyType=?2")
    public List<Biology> findByorderVice(String orderVice,String biologyType);

    @Query(value = "select b from Biology b where b.family=?1 and b.biologyType=?2")
    public List<Biology> findByfamily(String family,String biologyType);

    @Query(value = "select max(b.bid) from Biology b")
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
    @Query(value = "insert into biology (bid,chinese_name,professor_name,protect_grade,color1,family,family_english,color2,order_vice,order_vice_english,color3,information,exist_information,biology_type) values (?1,?2,?3," +
            "?4,?5,?6,?7,?8,?9,?10,?11,?12,?13,?14)",nativeQuery = true)
    public void save(Long bid,String chineseName,String professorName,String protectGrade,String color1,String family,String familyEnglish,String color2,
                     String orderVice,String orderViceEnglish,String color3,String information,String existInformation,String biologyType);

    @Query(value = "select * from biology where biology_type=?3 order by bid desc limit ?1 offset ?2 ",nativeQuery = true)
    public List<Biology> findPage(int pageSize, int startPoint,String biologyType);

    @Query(value = "select count(bid) from biology where biology_type=?1",nativeQuery = true)
    public int findCount(String biologyType);

    public Biology findBybid(Long bid);

    @Query(value = "select * from biology where biology_type=?1",nativeQuery = true)
    public List<Biology> findAll(String biologyType);
}
