package com.harshil.mail;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SendMail {

	private final static String URL = "http://localhost:4200/create-password/";

	public static void sendmail(String email, long id) throws AddressException, MessagingException, IOException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("tempggl1@gmail.com", "gglpassword1");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("tempggl1@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
		msg.setSubject("Create a Password");
		msg.setContent("Click on following link to create a password for your account: " + URL + id, "text/html");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent("Click on following link to create a password for your account: " + URL + id,
				"text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
//		MimeBodyPart attachPart = new MimeBodyPart();

//		attachPart.attachFile("/var/tmp/image19.png");
//		multipart.addBodyPart(attachPart);
		msg.setContent(multipart);
		Transport.send(msg);
	}
}
