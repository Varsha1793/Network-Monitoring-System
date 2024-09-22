package com.verizon.netpulse.configuration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verizon.netpulse.configuration.model.WifiData;

public interface WifiDataRepository extends JpaRepository<WifiData,Integer>{
   Optional<WifiData> findByUserId(Integer userId);
}
