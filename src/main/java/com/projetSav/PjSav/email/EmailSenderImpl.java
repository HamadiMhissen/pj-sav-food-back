package com.projetSav.PjSav.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmailSenderImpl implements EmailSender{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);

    private final JavaMailSender mailSender ;
	
    @Override
	@Async
	public void send(String destinataire, String email) {
		try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(destinataire);
            helper.setSubject("Confirmer votre email");
            helper.setFrom("hamadi.mhissen@mlritconsulting.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("a echoue lors de l'envoi de mail", e);
            throw new IllegalStateException("a echoue lors de l'envoi de mail");
        }
		
	}

}
