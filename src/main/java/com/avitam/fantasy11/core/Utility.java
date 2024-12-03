package com.avitam.fantasy11.core;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Random;

public class Utility {
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public class OtpUtil {

        public static String generateOtp(int length) {
            String numbers = "0123456789";
            Random random = new Random();
            StringBuilder otp = new StringBuilder();

            for (int i = 0; i < length; i++) {
                otp.append(numbers.charAt(random.nextInt(numbers.length())));
            }
            return otp.toString();
        }
    }

}