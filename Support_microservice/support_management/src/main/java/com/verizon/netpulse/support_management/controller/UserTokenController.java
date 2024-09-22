package com.verizon.netpulse.support_management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.verizon.netpulse.support_management.model.UserToken;
import com.verizon.netpulse.support_management.service.UserTokenService;

@Controller
public class UserTokenController {
    
    @Autowired
    private UserTokenService userTokenService;

    @GetMapping("/token/user")
public String getTokenByUserId(@RequestParam("userId") Integer userId, Model model) {
    List<UserToken> token = userTokenService.getTokenByUserId(userId);
    //System.out.println("Fetched Tokens: " + token);
    if (!token.isEmpty()) {
        model.addAttribute("token", token);
    } 
    else {
         model.addAttribute("error", "No Tokens Raised" );
    }
    model.addAttribute("user_id", userId);
    System.err.println(model);
    return "tokenDetails";
}

    @GetMapping("/item/create")
    public String showCreateForm(@RequestParam("user_id") Integer userId,Model model) {
        UserToken item = new UserToken();
        item.setUserId(userId);
        model.addAttribute("item",item);
        return "createItem";
    }

    @PostMapping("/item/create")
    public String createItem(@RequestParam Integer userId,@RequestParam String category, @RequestParam String description, Model model) {
        UserToken item = userTokenService.saveItem(userId,category, description);
        model.addAttribute("item", item);
    // Fetch the tokens for the user
    List<UserToken> tokens = userTokenService.getTokenByUserId(userId);

    // Add tokens to the model
    if (!tokens.isEmpty()) {
        model.addAttribute("token", tokens);
    } else {
        model.addAttribute("error", "Token ID not found.");
    }

    model.addAttribute("user_id", userId);
    model.addAttribute("successMessage", "Item created successfully!");

    // Return the view for token details
    return "redirect:/token/user?userId=" +userId;
    }

    @GetMapping("/users")
    public String getUsersWithStatus(Model model) {
        List<UserToken> users = userTokenService.getUsersWithStatus();
        model.addAttribute("users", users);
        return "users";
    }

    @PutMapping("/updateStatus/{tokenId}")
    public ResponseEntity<String> updateStatus(@PathVariable("tokenId") Long tokenId) {
        boolean newStatus = true;
        userTokenService.updateUserStatus(tokenId, newStatus);
        return ResponseEntity.ok("Status updated successfully");
    }
}