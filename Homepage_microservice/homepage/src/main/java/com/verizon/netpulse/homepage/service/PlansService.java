package com.verizon.netpulse.homepage.service;

import org.springframework.stereotype.Service;

import com.verizon.netpulse.homepage.model.Plans;
import com.verizon.netpulse.homepage.repository.PlansRepository;

@Service
public class PlansService {
    private final PlansRepository plansRepository;

    public PlansService(PlansRepository plansRepository)
    {
        this.plansRepository = plansRepository;
    }

    public Plans findPlansById(Integer id)
    {
        return plansRepository.findByPlanId(id);
    }
}
