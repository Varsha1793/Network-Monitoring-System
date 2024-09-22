package com.verizon.netpulse.support_management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.verizon.netpulse.support_management.model.UserToken;


public interface UserTokenRepository extends JpaRepository<UserToken,Long>{
    @Query("SELECT DISTINCT ut FROM UserToken ut WHERE ut.userId = :userId")
    List<UserToken> findByUserId(Integer userId);

    boolean existsByTokenId(Long tokenId);

    List<UserToken> findByStatusFalse();
}