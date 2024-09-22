package com.verizon.netpulse.configuration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.verizon.netpulse.configuration.model.Device;
import com.verizon.netpulse.configuration.model.WifiData;
import com.verizon.netpulse.configuration.service.DeviceService;
import com.verizon.netpulse.configuration.service.WifiDataService;

@Controller
public class WifiDataController {
    
    @Autowired
    private WifiDataService wifiDataService;

    @Autowired
    private DeviceService deviceService;

    @GetMapping("/wifi-details")
    public String getWifiDetails(@RequestParam Integer userId, Model model) {
        WifiData wifiData = wifiDataService.getWifiDetailsByUserId(userId).orElse(new WifiData());
        List<Device> activeDevices = deviceService.getActiveDevicesByUserId(userId);
        model.addAttribute("wifiDetails", wifiData);
        model.addAttribute("deviceList", activeDevices);
        return "wifi-details";
    }

    @PostMapping("/wifi-details/update")
    public String updateWifiDetails(@ModelAttribute WifiData wifiData) {
        wifiDataService.saveWifiDetails(wifiData);
        return "redirect:/wifi-details?userId=" + wifiData.getUserId();
    }


    @PostMapping("/device/delete/{deviceId}/{userId}")
    public String deleteDevice(@PathVariable Integer deviceId, @PathVariable Integer userId, RedirectAttributes redirectAttributes) {
        deviceService.markDeviceAsInactive(deviceId);
        redirectAttributes.addFlashAttribute("message", "Device successfully marked as inactive.");
        return "redirect:/wifi-details?userId=" + userId;
    }
}