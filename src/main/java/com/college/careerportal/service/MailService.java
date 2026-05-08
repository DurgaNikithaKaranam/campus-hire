package com.college.careerportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String content) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // ✅ IMPORTANT FIXES
            helper.setFrom("Career Portal <yourgmail@gmail.com>");
            helper.setTo(to);
            helper.setSubject(subject);

            // ✅ HTML content (better than plain text)
            String htmlContent = """
                <div style="font-family: Arial; padding: 10px;">
                    <h2 style="color:#2c3e50;">🚀 New Job Opportunity</h2>

                    <p>%s</p>

                    <hr>
                    <p style="font-size:12px;color:gray;">
                        This is an automated email from Career Portal
                    </p>
                </div>
            """.formatted(content.replace("\n", "<br>"));

            helper.setText(htmlContent, true); // ✅ TRUE = HTML

            mailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}