package com.example.demo.repository;

import com.example.demo.entity.AreaInfor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaInforRepository extends JpaRepository<AreaInfor,Long> {
    public AreaInfor findBytype(String type);
}
