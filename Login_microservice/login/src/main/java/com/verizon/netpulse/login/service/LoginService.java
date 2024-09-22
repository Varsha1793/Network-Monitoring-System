package com.verizon.netpulse.login.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.verizon.netpulse.login.model.UserLogin;
import com.verizon.netpulse.login.repository.LoginRepository;

@Service
public class LoginService {
    private final LoginRepository loginRepository;
    // private final RestTemplate restTemplate;
    
    public LoginService( LoginRepository loginRepository, RestTemplate restTemplate) {
        this.loginRepository = loginRepository;
        // this.restTemplate = restTemplate;
    }

    public UserLogin authenticate(String phoneNumber, String password) {
        System.out.println(phoneNumber);
        //return loginRepository.findByPhoneNumberAndPassword(phoneNumber, password).orElse(null);
        System.out.println("Authenticating user: " + phoneNumber);
        Optional<UserLogin> userLogin = loginRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        if (userLogin.isPresent()) {
            System.out.println("Authentication successful for user: " + phoneNumber);
        } else {
            System.out.println("Authentication failed for user: " + phoneNumber);
        }
        return userLogin.orElse(null);
    }

    public UserLogin findByPhoneNumber(String PhoneNumber)
    {
        return loginRepository.findByPhoneNumber(PhoneNumber);
    }

    public String redirectToHomePage(Integer userId) {
        // Redirect directly to the external URL
        return "redirect:http://localhost:8082/homepage?userId=" + userId;
    }
}
