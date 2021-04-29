package pers.hyu.jwtdemo.share.util;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import pers.hyu.jwtdemo.share.dto.UserDto;

public class AmazonSES {

    final String FROM = "hy.projects.wpg@gmail.com"; // sender email address
    final String SUBJECT = "Last step for complete your registration with the jwtdemo app"; // the subject of the email
    // html body for the email
    final String HTML_BODY = "<h1>Please verify your email address</h1>" +
            "<p>Thank you for registering with this app. To complete the registration, click on the following link:</p>" +
            "<a href='http://localhost:8081/email-verification.html?token=$tokenValue'>Last step to complete your registration</a>" +
            "<br/><br/>";
    // the email body for the receiver that with non-html client
    final String TEXT_BODY = "Please verify your email address" +
            "Thank you for registering with this app. To complete the registration, please open the following link:" +
            "http://localhost:8080/jwtdemo/users/email_verification?token=$tokenValue" +
            "This is the text body";


    final String RESET_PASSWORD_SUBJECT = "Please conform and reset the password"; // the subject of the email
    // html body for the email
    final String RESET_PASSWORD_HTML_BODY = "<h1>Hi, $firstName</h1>" +
            "<p>You got this email because you are request to reset your password.</p>" +
            "<p>If it was not you, please check your account security</p>" +
            "<p>If it was you, please click on the following link to reset your password within 2 hours:</p>" +
            "<a href='http://localhost:8081/reset-password-request.html?token=$tokenValue'>Reset the password</a>" +
            "<br/><br/>";
    final String RESET_PASSWORD_TEXT_BODY = "Hi, $firstName" +
            "You got this email because you are request to reset your password." +
            "If it was not you, please check your account security" +
            "If it was you, please click on the following link to reset your password within 2 hours:" +
            "<a href='http://localhost:8081/reset-password-request.html?token=$tokenValue'>Reset the password" ;

    public void sendVerifyEmail(UserDto userDto) {
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion("ca-central-1").build();
        String htmlBodyWithToken = HTML_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());
        String textBodyWithToken = TEXT_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());
        SendEmailRequest request = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(userDto.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);

        client.sendEmail(request);
//        System.out.println("Email has sent");
    }

    public void sendResetPasswordEmail(String firstName, String email, String resetPasswordToken){
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion("ca-central-1").build();
        String htmlBodyWithToken = RESET_PASSWORD_HTML_BODY.replace("$tokenValue", resetPasswordToken);
        htmlBodyWithToken = htmlBodyWithToken.replace("$firstName", firstName);

        String textBodyWithToken = RESET_PASSWORD_TEXT_BODY.replace("$tokenValue", resetPasswordToken);
        textBodyWithToken = textBodyWithToken.replace("$firstName", firstName);
         SendEmailRequest request = new SendEmailRequest()
                 .withDestination(new Destination().withToAddresses(email))
                 .withMessage(new Message()
                 .withSubject(new Content().withCharset("UTF-8").withData(RESET_PASSWORD_SUBJECT))
                 .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))))
                 .withSource(FROM);
         client.sendEmail(request);
        System.out.println("Reset password link sent!!!");

    }
}
