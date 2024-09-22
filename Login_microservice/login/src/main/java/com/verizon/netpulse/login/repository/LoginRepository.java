package com.verizon.netpulse.login.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.verizon.netpulse.login.model.UserLogin;

@Repository
public interface LoginRepository extends JpaRepository<UserLogin, Integer> {
    
    Optional<UserLogin> findByPhoneNumberAndPassword(String phoneNumber, String password);
    UserLogin findByPhoneNumber(String phoneNumber);
}
