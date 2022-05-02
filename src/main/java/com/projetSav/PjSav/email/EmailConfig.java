package com.projetSav.PjSav.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfig {
	
	public JavaMailSender mailSender() {
		return new JavaMailSenderImpl();
	}


}
