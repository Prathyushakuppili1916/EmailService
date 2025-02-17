
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;
import software.amazon.awssdk.regions.Region;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SendEmailLambdaTest {

    @Test
    public void testSESSendEmail() {
        // ✅ Initialize AWS SES Client
        SesClient sesClient = SesClient.builder().region(Region.AP_SOUTH_1).build();

        // ✅ Test Email Sending
        try {
            SendEmailRequest emailRequest = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses("saiprathyusha.kuppili@gmail.com").build())
                    .message(Message.builder()
                            .subject(Content.builder().data("Test Email").build())
                            .body(Body.builder()
                                    .text(Content.builder().data("This is a test email from AWS SES Lambda function.").build())
                                    .build())
                            .build())
                    .source("saiprathyusha.kuppili@gmail.com")
                    .build();

            sesClient.sendEmail(emailRequest);
            System.out.println("✅ Email sent successfully!");

            assertTrue(true); // Test passed if no exception is thrown
        } catch (SesException e) {
            fail("❌ Failed to send email: " + e.awsErrorDetails().errorMessage());
        } finally {
            sesClient.close();
        }
    }
}
