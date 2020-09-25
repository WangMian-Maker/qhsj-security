package com.example.demo.repository;

import com.example.demo.entity.PDF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PDFRepository extends JpaRepository<PDF,Long> {
//    private Long id;
//    private String name;
//    private String time;
//    private String path;
    @Modifying
    @Query(value = "insert into pdf (id,name,time,path) values (?1,?2,?3,?4)",nativeQuery = true)
    public void save(Long id,String name,String time,String path);

    public PDF findByid(Long id);

    @Query(value = "select max(id) from pdf",nativeQuery = true)
    public Long maxId();

    @Query(value = "select * from pdf order by id desc limit ?1 offset ?2",nativeQuery = true)
    public List<PDF> findPage(int pageSize,int startPoint);

}
