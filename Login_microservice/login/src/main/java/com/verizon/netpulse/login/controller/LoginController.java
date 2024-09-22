package com.verizon.netpulse.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.verizon.netpulse.login.model.UserLogin;
import com.verizon.netpulse.login.service.LoginService;
import com.verizon.netpulse.login.service.OTPService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final OTPService otpService;

    public LoginController(LoginService loginService, OTPService otpService) {
        this.loginService = loginService;
        this.otpService = otpService;
    }
    
    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("loginRequest", new UserLogin());
        return "login_page";
    }
      
    @PostMapping("/login")
    public String login(@ModelAttribute UserLogin userLogin, HttpSession session, Model model) {
        UserLogin authenticated = loginService.authenticate(userLogin.getPhoneNumber(), userLogin.getPassword());
        if (authenticated != null) {
            // Check if the user is Admin
            if ("Admin".equals(userLogin.getPhoneNumber()) && "Admin123".equals(userLogin.getPassword())) {
                return "redirect:http://localhost:8085/users";
            }

            // For other users, proceed with OTP
            String otp = otpService.generateOTP();
            otpService.sendOTP(userLogin.getPhoneNumber(), otp);
            session.setAttribute("otp", otp); // Store OTP in session for verification later
            session.setAttribute("phno", userLogin.getPhoneNumber());
            return "redirect:/otp_verification"; // Redirect to OTP verification page
        } else {
            String errorMessage = "Invalid Credentials";
            model.addAttribute("error", errorMessage);
            return "login_page";
        }
    }

    @GetMapping("/otp_verification")
    public String getOtpVerificationPage(Model model) {
        return "otp_verification"; // Redirect to OTP verification page
    }

    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam("otp") String otp, HttpSession session, Model model) {
        String sessionOtp = (String) session.getAttribute("otp");
        String phoneNumber = (String) session.getAttribute("phno");

        // If the OTP stored in session matches the OTP provided by the user
        if (sessionOtp != null && sessionOtp.equals(otp)) {
            session.removeAttribute("otp"); // Remove OTP from session
            UserLogin userLogin = loginService.findByPhoneNumber(phoneNumber);
        
        if (userLogin != null) {
            Integer userId = userLogin.getUserId();
            return loginService.redirectToHomePage(userId);
        } else {
            model.addAttribute("error", "User not found");
            return "otp_verification";
        }
        } else {
            String errorMessage = "Invalid OTP";
            model.addAttribute("error", errorMessage);
        
            return "otp_verification";
        }
    }


}
