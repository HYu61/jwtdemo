package pers.hyu.jwtdemo.security;


import org.springframework.beans.factory.annotation.Autowired;
import pers.hyu.jwtdemo.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 1000*60*20; // 20 minuts
    public static final long SIGN_UP_EMAIL_EXPIRATION_TIME = 1000*60*60*24; // 1 day
    public static final long RESET_PASSWORD_EMAIL_EXPIRATION_TIME = 1000*60*60*2; // 2 hours
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String VERIFICATION_EMAIL_URL = "/users/email_verification";
    public static final String RESET_PASSWORD_REQUEST_URL = "/users/reset_password_request";
    public static final String RESET_PASSWORD_URL = "/users/reset_password";

    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("appProperties");
        return appProperties.getTokenSecret();

    }
}
