package com.verizon.netpulse.homepage.controller;

import com.verizon.netpulse.homepage.model.Users;
import com.verizon.netpulse.homepage.model.Plans;
import com.verizon.netpulse.homepage.model.Services;
import com.verizon.netpulse.homepage.service.UsersService;
import com.verizon.netpulse.homepage.service.PlansService;
import com.verizon.netpulse.homepage.service.ServicesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomepageController {

    private final UsersService usersService;
    private final ServicesService servicesService;
    private final PlansService plansService;

    public HomepageController(UsersService usersService, ServicesService servicesService, PlansService plansService)
    {
        this.usersService = usersService;
        this.servicesService = servicesService;
        this.plansService = plansService;
    }

    @GetMapping("/homepage")
    public String getUserDetails(@RequestParam("userId") Integer userId, Model model) {
        Users user = usersService.findUserDetails(userId);
        if (user == null) {
            return "error"; // Return an error view if user is not found
        }

        Services services = servicesService.findServices(userId);
        Plans plans = plansService.findPlansById(services.getPlanId());

        model.addAttribute("user", user);
        model.addAttribute("services", services);
        model.addAttribute("plans", plans);

        return "homepage"; // Thymeleaf template name
    }

    @GetMapping("/profile")
    public String showProfile(@RequestParam("userId") Integer userId, Model model) {
        // Fetch user profile by phoneNumber
        Users user = usersService.findUserDetails(userId);
    
        if (user != null) {
            model.addAttribute("user", user);
            System.out.println("User fetched: " + user.toString());
        } else {
            System.out.println("No user found : " + userId);
        }
    
        return "profile";
    }

    @PostMapping("/redirectToProfile")
    public String redirectToProfile(@RequestParam("userId") Integer userId) {
        // Redirect to /profile with phoneNumber as a parameter
        return "redirect:/profile?userId=" + userId;
    }
}
