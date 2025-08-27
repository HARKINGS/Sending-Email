package com.fpt.harkins.mailService.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.fpt.harkins.mailService.dto.request.MailRequest;
import com.fpt.harkins.mailService.dto.response.MailResponse;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private FreeMarkerConfigurer freemarkerConfigurer;

    public MailResponse sendEmail(MailRequest request, Map<String, Object> model) {
        MailResponse response = new MailResponse();
        MimeMessage message = sender.createMimeMessage();
        try {
            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            // add attachment
//            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            helper.addAttachment("logo.png", new ClassPathResource("static/images/logo.png"));

            Template t = freemarkerConfigurer.getConfiguration().getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            helper.setTo(request.getTo());
            helper.setText(html, true);
            helper.setSubject(request.getSubject());
            helper.setFrom(request.getFrom());
            sender.send(message);

            response.setMessage("mail send to : " + request.getTo());
            response.setStatus(Boolean.TRUE);

        } catch (MessagingException e) {
            response.setMessage("Mail sending failure (SMTP): " + e.getMessage());
            response.setStatus(false);
        } catch (IOException | TemplateException e) {
            response.setMessage("Template/attachment failure: " + e.getMessage());
            response.setStatus(false);
        }

        return response;
    }


}