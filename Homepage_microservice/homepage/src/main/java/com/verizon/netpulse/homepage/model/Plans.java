package com.verizon.netpulse.homepage.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "plans")
public class Plans {
    
    @Id
    @Column(name = "plan_id")
    private Integer planId;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "data_limit", nullable = false)
    private Integer dataLimit;

    @Column(name = "validity", nullable = false)
    private Integer validity;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "sms", nullable = false)
    private Integer sms;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedAt = LocalDateTime.now();
    }
}
