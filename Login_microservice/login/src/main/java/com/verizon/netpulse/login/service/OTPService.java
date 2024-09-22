package com.verizon.netpulse.login.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

    private static final String ACCOUNT_SID = "AC7def7f705458f6ae01fa311c484bf351";
    private static final String AUTH_TOKEN = "82221f91749c656487552f7c70efe532";
    private static final String TWILIO_PHONE_NUMBER = "+13512072228";
    private static final String DEFAULT_COUNTRY_CODE = "+91"; 

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendOTP(String toPhoneNumber, String otp) {
        String formattedPhoneNumber = formatPhoneNumber(toPhoneNumber);
        String messageBody = "To complete your login to NET PULSE, please enter the following verification code in the Netpulse application: " + otp;
        Message.creator(
                new PhoneNumber(formattedPhoneNumber),
                new PhoneNumber(TWILIO_PHONE_NUMBER),
                messageBody
        ).create();
    }

    public String generateOTP() {
        // Generate a 6-digit OTP
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (!phoneNumber.startsWith("+")) {
            // Prepend the default country code if it's not present
            phoneNumber = DEFAULT_COUNTRY_CODE + phoneNumber;
        }
        return phoneNumber;
    }
}
