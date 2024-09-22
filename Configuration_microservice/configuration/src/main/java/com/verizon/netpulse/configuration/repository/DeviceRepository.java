package com.verizon.netpulse.configuration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.verizon.netpulse.configuration.model.Device;

public interface DeviceRepository extends JpaRepository<Device,Integer>{
    List<Device> findByUserIdAndStatusTrue(Integer userId);
}
