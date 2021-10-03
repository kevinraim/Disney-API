package com.apidisney.disney.mailsender;

import org.springframework.stereotype.Service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class MailService {
	private static final Logger logger = LoggerFactory.getLogger(MailService.class);
	
	public String sendTextEmail(String emailTo) throws IOException {

		    Email from = new Email("kevinraimolugo@gmail.com");
		    String subject = "Example";
		    Email to = new Email(emailTo);
		    Content content = new Content("text/plain", "Welcome to Diseny, new user, mi name is Kevin! I'm very happy to meet you, hope you like my API");
		    Mail mail = new Mail(from, subject, to, content);
		
		    SendGrid sg = new SendGrid("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");
		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      logger.info(response.getBody());
		      return response.getBody();	     
		    } catch (IOException ex) {
		      throw ex;
		    }	   
	}
}
