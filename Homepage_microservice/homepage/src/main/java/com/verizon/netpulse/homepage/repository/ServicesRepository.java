package com.verizon.netpulse.homepage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.netpulse.homepage.model.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services,Integer>{
    Services findByUserId(Integer userId);
}
