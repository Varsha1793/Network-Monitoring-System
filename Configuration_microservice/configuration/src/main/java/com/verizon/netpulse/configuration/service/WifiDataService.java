package com.verizon.netpulse.configuration.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.netpulse.configuration.model.WifiData;
import com.verizon.netpulse.configuration.repository.WifiDataRepository;



@Service
public class WifiDataService {
    @Autowired
    private WifiDataRepository wifiDataRepository;

    public Optional<WifiData> getWifiDetailsByUserId(Integer userId) {
        return wifiDataRepository.findByUserId(userId);
    }

    public WifiData saveWifiDetails(WifiData wifiData) {
        return wifiDataRepository.save(wifiData);
    }
}