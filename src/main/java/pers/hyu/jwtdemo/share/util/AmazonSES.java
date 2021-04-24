package pers.hyu.jwtdemo.share.util;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
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
}
