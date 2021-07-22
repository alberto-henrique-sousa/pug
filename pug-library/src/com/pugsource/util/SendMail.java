package com.pugsource.util;
/**
 * Pug Framework
 * 
 * @author Alberto Henrique Sousa
 * 
 * License: GPL (Free - Open Source)
 */

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class SendMail
 * 
 *  Classe responsável pelo envio de e-mail através do GMail
 *  
 * @author Alberto Henrique Sousa
 *
 */
public class SendMail {   

	private String mailSMTPServer;   
	private String mailSMTPServerPort;
	private String mailSMTPuser;
	private String mailSMTPpassword;

	/**
	 * Método construtor.
	 */
	public SendMail() {    
		mailSMTPServer = "smtp.gmail.com";   
		mailSMTPServerPort = "465";
		mailSMTPuser = "????@gmail.com";
		mailSMTPpassword = "????";
	}   

	/**
	 * Método construtor.
	 * 
	 * @param mailSMTPServer 
	 * @param mailSMTPServerPort
	 * @param mailSMTPuser
	 * @param mailSMTPpassword
	 */
	public SendMail(String mailSMTPServer, String mailSMTPServerPort, String mailSMTPuser, String mailSMTPpassword) { //Para outro Servidor   
		this.mailSMTPServer = mailSMTPServer;   
		this.mailSMTPServerPort = mailSMTPServerPort;
		this.mailSMTPuser = mailSMTPuser;
		this.mailSMTPpassword = mailSMTPpassword;
	}   

	/**
	 * Envia a mensagem
	 * 
	 * @param to
	 * @param replyTo
	 * @param subject
	 * @param message
	 */
	public boolean sendMail(String to, String replyTo, String subject, String message) {   

		try {
			Properties props = new Properties();   

			// SERVIDOR PROXY   
			/*  
                props.setProperty("proxySet","true");  
                props.setProperty("socksProxyHost","192.168.155.1"); // IP do Servidor Proxy  
                props.setProperty("socksProxyPort","1080");  // Porta do servidor Proxy  
			 */   

			props.put("mail.transport.protocol", "smtp");    
			props.put("mail.smtp.starttls.enable","true");   
			props.put("mail.smtp.host", mailSMTPServer);    
			props.put("mail.smtp.auth", "true");    
			props.put("mail.smtp.user", mailSMTPuser);    
			props.put("mail.debug", "true");   
			props.put("mail.smtp.port", mailSMTPServerPort);    
			props.put("mail.smtp.socketFactory.port", mailSMTPServerPort);    
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
			props.put("mail.smtp.socketFactory.fallback", "false");   
			props.put("mail.mime.charset", "UTF-8"); 

			SimpleAuth auth = null;   
			auth = new SimpleAuth (mailSMTPuser,mailSMTPpassword);   

			Session session = Session.getDefaultInstance(props, auth);   
			session.setDebug(true);    

			Message msg = new MimeMessage(session);   

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, true));
			msg.setFrom(new InternetAddress(mailSMTPuser));   
			msg.setSubject(subject);   
			msg.setContent(message,"text/html;charset=\"UTF-8\"");
			if (replyTo != null && replyTo.length() > 0) {
				msg.setReplyTo(InternetAddress.parse(replyTo, true));
			}    


			Transport tr;   
			tr = session.getTransport("smtp");    
			tr.connect(mailSMTPServer, mailSMTPuser, mailSMTPpassword);   
			msg.saveChanges();

			tr.sendMessage(msg, msg.getAllRecipients());   
			tr.close();
			return true;
		} catch (Exception e) {   
			e.printStackTrace();
			return false;
		}   
	}   
}   

/**
 * Class SimpleAuth
 * 
 * Classe para autenticação
 * 
 * @author Alberto Henrique Sousa
 *
 */
class SimpleAuth extends Authenticator {   
	public String username = null;   
	public String password = null;   


	/**
	 * Método construtor.
	 * 
	 * @param user
	 * @param pwd
	 */
	public SimpleAuth(String user, String pwd) {   
		username = user;   
		password = pwd;   
	}   

	/**
	 * Usuário e senha para autenticação
	 */	
	protected PasswordAuthentication getPasswordAuthentication() {   
		return new PasswordAuthentication (username,password);   
	}   
}   
