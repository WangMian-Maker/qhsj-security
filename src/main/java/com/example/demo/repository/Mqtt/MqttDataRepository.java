package com.example.demo.repository.Mqtt;

import com.example.demo.entity.MqttData.MqttData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MqttDataRepository extends JpaRepository<MqttData,Long> {
}
