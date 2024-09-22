package com.verizon.netpulse.configuration.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Wifi")
public class WifiData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wifiId;
    private String wifiName;
    private String wifiPassword;
    private Integer userId; 
}