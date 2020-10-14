package com.example.demo.repository;

import com.example.demo.entity.biology.BiologyString;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiologyStringRepository extends JpaRepository<BiologyString,Long> {
    String findAll="select * from biology_string order by id";

    @Query (value = findAll,nativeQuery = true)
    public List<BiologyString> mFindAll();
}
