package com.verizon.netpulse.homepage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.netpulse.homepage.model.Plans;

@Repository
public interface PlansRepository extends JpaRepository<Plans,Integer>{
    Plans findByPlanId(Integer planId);
}
