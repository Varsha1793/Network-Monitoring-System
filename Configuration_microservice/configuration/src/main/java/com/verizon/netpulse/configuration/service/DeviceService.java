package com.verizon.netpulse.configuration.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.verizon.netpulse.configuration.model.Device;
import com.verizon.netpulse.configuration.repository.DeviceRepository;

@Service
public class DeviceService {

    @Autowired
    public DeviceRepository deviceRepository;

    public List<Device> getActiveDevicesByUserId(Integer userId) {
        return deviceRepository.findByUserIdAndStatusTrue(userId);
    }

    @Transactional
    public void markDeviceAsInactive(Integer deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        device.setStatus(false);
        deviceRepository.save(device);
    }
}
