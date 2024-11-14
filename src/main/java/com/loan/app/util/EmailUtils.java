package com.loan.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;


@Component
public class EmailUtils {
	
   private	Logger logger=LoggerFactory.getLogger(EmailUtils.class);

	
	@Autowired
	private JavaMailSender mailSender;
	
	public Boolean sendEmail(String to,String subject, String body) {
		try {
			
			MimeMessage message=mailSender.createMimeMessage();
			
			MimeMessageHelper helper=new MimeMessageHelper(message);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body,true);
			mailSender.send(message);
			
			return true;
		} catch (Exception e) {
			// TODO: handle exception
            logger.error(e.getMessage());
			return false;
		}
		
		
	}
}
