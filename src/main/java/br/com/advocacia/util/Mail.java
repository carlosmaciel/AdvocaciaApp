package br.com.advocacia.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	private static void sendEmail(Session session, String fromEmail, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress(fromEmail, "Advocacia App"));

	      msg.setReplyTo(InternetAddress.parse(toEmail, false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setContent(body, "text/html");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);  

	      System.out.println("Email Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public static boolean sendEmail(String body) {		
	    System.out.println("SimpleEmail Start");
	    	    
	    try {
	    	String auxiliarUser = null;
			String auxiliarPsw = null;
			String stmp = null;
			String port = null;
			String from = null;
			String to = null;
			
	    	File myObj = new File("C:/advocaciamail/mail.cfg");
	        Scanner myReader = new Scanner(myObj);
	        while (myReader.hasNextLine()) {
	          String data = myReader.nextLine();
	          String[] split = data.split("="); 
	          
	          if(split[0].equals("user")) {
	        	  auxiliarUser = split[1];
	          } else if(split[0].equals("password")) {
	        	  auxiliarPsw = split[1];
	          } else if(split[0].equals("smtp")) {
	        	  stmp = split[1];
	          } else if(split[0].equals("port")) {
	        	  port = split[1];
	          } else if(split[0].equals("to")) {
	        	  to = split[1];
	          } else if(split[0].equals("from")) {
	        	  from = split[1];
	          }
	          
	          System.out.println(data);
	        }
	        myReader.close();
	    	
	        final String user = auxiliarUser;
			final String password = auxiliarPsw;
			
	    	System.out.println("TLSEmail Start");
			Properties props = new Properties();
			props.put("mail.smtp.host", stmp); //SMTP Host
			props.put("mail.smtp.port", port); //TLS Port
			props.put("mail.smtp.auth", "true"); //enable authentication
			props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
			
	                //create Authenticator object to pass in Session.getInstance argument
			Authenticator auth = new Authenticator() {
				//override the getPasswordAuthentication method
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, password);
				}
			};
		    
		    Session session = Session.getInstance(props, auth);
		    
		    sendEmail(session, from, to,"Advocacia App", body);
		    
		    return true;
	    } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
	    return false;
	}
}