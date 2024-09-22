package com.verizon.netpulse.homepage.service;

import org.springframework.stereotype.Service;

import com.verizon.netpulse.homepage.model.Users;
import com.verizon.netpulse.homepage.repository.UsersRepository;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository)
    {
        this.usersRepository = usersRepository;
    }

    public Users findUserDetails(Integer userId)
    {
        return usersRepository.findByUserId(userId);
    }

    public String redirectToPerformancepage(Integer userId)
    {
        return "redirect:http://localhost:8083/performance?userId="+userId;
    }
}
