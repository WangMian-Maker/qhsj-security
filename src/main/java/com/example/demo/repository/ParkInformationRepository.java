package com.example.demo.repository;

import com.example.demo.entity.ParkInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParkInformationRepository extends JpaRepository<ParkInformation,Long> {
    @Query(value = "select p from ParkInformation p where p.pid=(select max(p.pid) from ParkInformation p)")
    public ParkInformation find();
}
