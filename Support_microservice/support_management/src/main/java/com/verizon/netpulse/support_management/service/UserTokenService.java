package com.verizon.netpulse.support_management.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.verizon.netpulse.support_management.model.UserToken;
import com.verizon.netpulse.support_management.repository.UserTokenRepository;


@Service
public class UserTokenService {
    
    @Autowired
    private UserTokenRepository userTokenRepository;

    private final Random random = new Random();

    public List<UserToken> getTokenByUserId(Integer userId) {
        List<UserToken> tokens = userTokenRepository.findByUserId(userId);
        //System.out.println("Fetched Tokens: " + tokens);
        return tokens;
    }


    public UserToken saveItem(Integer userId,String category, String description) {
        long tokenId = generateUniqueTokenId();
        UserToken item = new UserToken();
        item.setTokenId(tokenId);
        item.setUserId(userId);
        item.setCategory(category);
        item.setDescription(description);
        item.setStatus(false);
        return userTokenRepository.save(item);
    }

    private long generateUniqueTokenId() {
        long tokenId;
        do {
            tokenId = random.nextInt(900) + 100; // Generates a random number between 100 and 999
        } while (userTokenRepository.existsByTokenId(tokenId)); // Checks if the tokenId already exists
        return tokenId;
    }

    public List<UserToken> getUsersWithStatus() {
      //  return userTokenRepository.findByStatusFalse();
      return userTokenRepository.findAll();
    }

    // public void saveUser(UserToken user) {
    //     userTokenRepository.save(user);
    // }

    // public UserToken getUserById(Long id){
    //     Optional<UserToken> userOptional = userTokenRepository.findById(id);
    //     if (userOptional.isPresent()) {
    //         return userOptional.get();
    //     }
    //     return null; 
    // }

    public void updateUserStatus(Long tokenId, boolean status) {
        Optional<UserToken> userOptional = userTokenRepository.findById(tokenId);
        if (userOptional.isPresent()) {
            UserToken user = userOptional.get();
            user.setStatus(status);
            userTokenRepository.save(user); // Save the updated user back to the database
        }
    }
}