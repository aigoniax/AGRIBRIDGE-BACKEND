package com.agribridge.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class EmailService {

    @Value("${resend.api.key}")
    private String apiKey;

    private void sendEmail(String toEmail, String toName, String subject, String htmlContent) {
        try {
            String json = "{"
                    + "\"from\": \"AgriBridge <onboarding@resend.dev>\","
                    + "\"to\": [\"" + toEmail + "\"],"
                    + "\"subject\": \"" + subject + "\","
                    + "\"html\": \"" + htmlContent.replace("\"", "\\\"").replace("\n", "") + "\""
                    + "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.resend.com/emails"))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                System.out.println("✅ Email sent successfully to: " + toEmail);
            } else {
                System.err.println("❌ Failed to send email: " + response.body());
            }
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