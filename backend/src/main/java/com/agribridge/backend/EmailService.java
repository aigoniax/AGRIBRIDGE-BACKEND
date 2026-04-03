package com.agribridge.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private void sendEmail(String toEmail, String toName, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail, "AgriBridge");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("✅ Email sent successfully to: " + toEmail);
        } catch (Exception e) {
            System.err.println("❌ Failed to send email: " + e.getMessage());
        }
    }

    public void sendRegistrationPending(String toEmail, String fullName) {
        String subject = "AgriBridge - Registration Pending Approval";
        String html = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #2D6A4F;'>Welcome to AgriBridge, " + fullName + "!</h2>"
                + "<p>Thank you for registering. Your account is currently <strong>pending approval</strong> from our admin team.</p>"
                + "<p>You will receive another email once your account has been reviewed.</p>"
                + "<br><p style='color: #888;'>The AgriBridge Team</p>"
                + "</div>";
        sendEmail(toEmail, fullName, subject, html);
    }

    public void sendRegistrationApproved(String toEmail, String fullName) {
        String subject = "AgriBridge - Registration Approved!";
        String html = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #2D6A4F;'>Congratulations, " + fullName + "!</h2>"
                + "<p>Your AgriBridge account has been <strong style='color: green;'>approved</strong>.</p>"
                + "<p>You can now log in and start using the platform.</p>"
                + "<br><p style='color: #888;'>The AgriBridge Team</p>"
                + "</div>";
        sendEmail(toEmail, fullName, subject, html);
    }

    public void sendRegistrationRejected(String toEmail, String fullName) {
        String subject = "AgriBridge - Registration Status Update";
        String html = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #2D6A4F;'>Hello, " + fullName + "</h2>"
                + "<p>We regret to inform you that your AgriBridge registration has been <strong style='color: red;'>rejected</strong>.</p>"
                + "<p>If you believe this is a mistake, please contact our support team.</p>"
                + "<br><p style='color: #888;'>The AgriBridge Team</p>"
                + "</div>";
        sendEmail(toEmail, fullName, subject, html);
    }

    public void sendListingPending(String toEmail, String fullName, String listingTitle) {
        String subject = "AgriBridge - Listing Pending Review";
        String html = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #2D6A4F;'>Hello, " + fullName + "!</h2>"
                + "<p>Your listing <strong>" + listingTitle + "</strong> is currently <strong>pending review</strong>.</p>"
                + "<p>You will be notified once it has been reviewed by our admin team.</p>"
                + "<br><p style='color: #888;'>The AgriBridge Team</p>"
                + "</div>";
        sendEmail(toEmail, fullName, subject, html);
    }

    public void sendListingApproved(String toEmail, String fullName, String listingTitle) {
        String subject = "AgriBridge - Listing Approved!";
        String html = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #2D6A4F;'>Great news, " + fullName + "!</h2>"
                + "<p>Your listing <strong>" + listingTitle + "</strong> has been <strong style='color: green;'>approved</strong>.</p>"
                + "<p>It is now visible to buyers on AgriBridge.</p>"
                + "<br><p style='color: #888;'>The AgriBridge Team</p>"
                + "</div>";
        sendEmail(toEmail, fullName, subject, html);
    }

    public void sendListingRejected(String toEmail, String fullName, String listingTitle) {
        String subject = "AgriBridge - Listing Status Update";
        String html = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                + "<h2 style='color: #2D6A4F;'>Hello, " + fullName + "</h2>"
                + "<p>Your listing <strong>" + listingTitle + "</strong> has been <strong style='color: red;'>rejected</strong>.</p>"
                + "<p>Please review your listing details and try again.</p>"
                + "<br><p style='color: #888;'>The AgriBridge Team</p>"
                + "</div>";
        sendEmail(toEmail, fullName, subject, html);
    }
}