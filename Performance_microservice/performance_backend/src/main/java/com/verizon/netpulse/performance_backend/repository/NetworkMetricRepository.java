package com.verizon.netpulse.performance_backend.repository;

import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.netpulse.performance_backend.model.NetworkMetrics;

@Repository
public interface NetworkMetricRepository extends JpaRepository<NetworkMetrics, Long> {

    List<NetworkMetrics> findByUserId(Integer userId);

    List<NetworkMetrics> findByUserIdAndCreatedAtAfter(Integer userId, LocalDateTime oneHourAgo);
}

