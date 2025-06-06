package d365;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;
import java.io.File;
import java.util.logging.Logger;

public class EmailReportSender {

    // Logger to log email sending process
    private static final Logger logger = Logger.getLogger(EmailReportSender.class.getName());

    public static void sendReport(List<String> toEmails, List<String> reportPaths) {
        final String fromEmail = System.getenv("EMAIL_USERNAME");
        final String password = System.getenv("EMAIL_PASSWORD");

        if (fromEmail == null || password == null) {
            logger.severe("Email credentials are not set in environment variables.");
            System.err.println("Email credentials are missing. Please set EMAIL_USERNAME and EMAIL_PASSWORD environment variables.");
            return;
        }

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a new session for email sending
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                logger.info("Authenticating SMTP session");
                System.out.println("Authenticating SMTP session");
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));

            // Join multiple email addresses into a comma-separated string and set recipients
            String recipientAddresses = String.join(",", toEmails);
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientAddresses));

            message.setSubject("Cucumber Test Execution Reports");

            // Prepare the email body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Hi,\n\nPlease find the attached test execution reports.\n\nRegards,\nAutomation Team");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Process each report and add them as attachments
            for (String path : reportPaths) {
                File file = new File(path);
                if (file.exists() && file.length() > 0) {
                    logger.info("Attaching report: " + path);
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(file);
                    multipart.addBodyPart(attachmentPart);
                } else {
                    logger.warning("Skipping missing or empty report: " + path);
                    System.out.println("Skipping missing or empty report: " + path);
                }
            }

            // Set the multipart content (body + attachments) to the email
            message.setContent(multipart);

            // Send the email
            logger.info("Sending email with attachments...");
            System.out.println("Sending email...");

            Transport.send(message);

            logger.info("Email sent successfully with multiple attachments.");
            System.out.println("Email sent successfully with multiple attachments.");

        } catch (Exception e) {
            logger.severe("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
