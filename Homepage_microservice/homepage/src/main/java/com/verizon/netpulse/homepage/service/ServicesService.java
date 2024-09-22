package com.verizon.netpulse.homepage.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.verizon.netpulse.homepage.model.Plans;
import com.verizon.netpulse.homepage.model.Services;
import com.verizon.netpulse.homepage.repository.PlansRepository;
import com.verizon.netpulse.homepage.repository.ServicesRepository;

@Service
public class ServicesService {

    private ServicesRepository servicesRepository;
    private PlansRepository plansRepository;

    public ServicesService(ServicesRepository servicesRepository,PlansRepository plansRepository)
    {
        this.servicesRepository = servicesRepository;
        this.plansRepository = plansRepository;
    }

    //this method will automatically assign the value for expiry field by processing the plan table
    public void updateExpiryDate(Services service) {
        Plans plan = plansRepository.findByPlanId(service.getPlanId());
        if (plan != null) {
            LocalDateTime startDate = service.getStartDate();
            Integer validityDays = plan.getValidity();
            LocalDateTime expiryDate = startDate.plusDays(validityDays);
            service.setExpiryDate(expiryDate);
            servicesRepository.save(service);
        }
    }

    //to get the service of the user by their id
    public Services findServices(Integer userId)
    {
        return servicesRepository.findByUserId(userId);
    }
}
