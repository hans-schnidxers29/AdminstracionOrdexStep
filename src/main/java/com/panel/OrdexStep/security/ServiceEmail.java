package com.panel.OrdexStep.security;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class ServiceEmail {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendEmail(List<?>toRecep, String asunto, Map<String, Object> modelos, String RutaTemplate, boolean cobro){
        try{
            for(Object item : toRecep) {
                String email = String.valueOf(item);
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

                Context context = new Context();
                context.setVariables(modelos);
                String html = templateEngine.process(RutaTemplate, context);

                helper.setTo(email);
                helper.setSubject(asunto);
                helper.setText(html, true); // "true" activa el renderizado de HTML
                helper.setFrom("haxliou@gmail.com");

                helper.addInline("logoOrdex", new ClassPathResource("static/img/LogoEmail.png"));
                if (cobro){
                    helper.addInline("qrPago", new ClassPathResource("static/img/QR-PAGO.jpg"));
                }

                mailSender.send(message);
            }
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }



}
