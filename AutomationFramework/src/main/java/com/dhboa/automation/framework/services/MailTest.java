package com.dhboa.automation.framework.services;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;


public class MailTest {
  
	public static void main(String[] args) throws AddressException, MessagingException {
		 try{
			 Properties props = new Properties();
		 
				 props.put("mail.smtp.host", "smtp.gmail.com");
props.put("mail.smtp.port", "587");
					
props.put("mail.smtp.auth", "true"); //enable authentication
props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        //create Authenticator object to pass in Session.getInstance argument
Authenticator auth = new Authenticator() {
	//override the getPasswordAuthentication method
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("token.verify@gmail.com", "darkhorseboa1234567890");
	}
};
	Session session = Session.getInstance(props, auth);
	Message msg = new MimeMessage(session);
/*	msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
    msg.addHeader("format", "flowed");
    msg.addHeader("Content-Transfer-Encoding", "8bit");*/
	msg.setFrom(new InternetAddress("token.verify@gmail.com"));
	msg.addRecipient(RecipientType.TO, new InternetAddress("jyothireddy.guduru@darkhorseboa.com"));
	msg.setSubject("Mail test");
	msg.setText("mail test");
	
Transport.send(msg);
System.out.println("sent");
	}

	catch(Exception e){
		e.printStackTrace();

/*"token.verify@gmail.com", "darkhorseboa1234567890"*/
/*"vyshnavi.mudumby@darkhorseboa.com", "vyshnavi27"
*/	}
}
}