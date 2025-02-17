package org.example;

import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;
import software.amazon.awssdk.regions.Region;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SendEmailLambda implements RequestHandler<Object, String> {

    private static final String FROM_EMAIL = "saiprathyusha.kuppili@gmail.com"; // SES Verified Email
    private static final String TO_EMAIL = "saiprathyusha.kuppili@gmail.com";   // SES Verified Recipient
    private static final String SUBJECT = "Good Morning!";
    private static final String BODY_TEXT = "Good morning! Have a great day!";

    @Override
    public String handleRequest(Object input, Context context) {
        SesClient sesClient = SesClient.builder().region(Region.AP_SOUTH_1).build();

        try {
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(TO_EMAIL).build())
                    .message(Message.builder()
                            .subject(Content.builder().data(SUBJECT).build())
                            .body(Body.builder()
                                    .text(Content.builder().data(BODY_TEXT).build())
                                    .build())
                            .build())
                    .source(FROM_EMAIL)
                    .build();

            sesClient.sendEmail(emailRequest);
            return "Email sent successfully!";
        } catch (SesException e) {
            return "Failed to send email: " + e.awsErrorDetails().errorMessage();
        } finally {
            sesClient.close();
        }
    }
}
