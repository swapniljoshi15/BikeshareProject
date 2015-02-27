
package util;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailNotification {

	static final String host = "smtp.gmail.com";
	static final String username = "bikeshare273";
	static final String password = "sithu@13"; 
	
	/*public static void main(String... args) throws UnsupportedEncodingException	
	{

		String userEmailAdress, userName;
		userEmailAdress = "puneetpopli.26pp@gmail.com";
		userName = "Puneet";
		String fromLocation = "San Jose State Univerity";
		int from = 6;
		int to = 7;
		int bike= 9845;
		String unlockCode = "A9Y20P"; 

		gmail(userEmailAdress, userName, fromLocation, from, to, bike, unlockCode);

	}*/


	public static void sendEmailonSignUp(String userEmailAddress, String userName)  throws UnsupportedEncodingException 
	{
		String subject = "Welcome to Spartan Bike Share Family!";
		
			String msgBody = "Hello "+ userName + ",\n\nYour account has been successfully created." +
				"\n\n Thank you for choosing Spartan BikeShare" + "\n\n Enjoy the green rides ! !\n\n" + 
				"Team Spartan !!! ";

			emailGenerator(userEmailAddress, userName, subject, msgBody);
		

	}
	
	public static void sendEmailonReservation(String userEmailAddress, String userName, String fromLocation, int fromInt, int toInt, int txidInt, String bike, String unlockCode)  throws UnsupportedEncodingException 
	{

		String subject = "Your booking has been confirmed. Enjoy the ride!";
		String from = fromInt+"";
		String to = toInt+"";
		String txid = txidInt+"";
		String msgBody = "Hello "+ userName + ",\n\nYour booking [Tx Ref :"+ txid +"] from " + fromLocation +
				" for time slot " + from + " to " + to + " was successful !\n" +
				"\nReserved Bike Id : " + bike + "\nUnlock Code " + unlockCode + 
				"\n\n Thanks for choosing Spartan BikeShare" + 
				"\n\n Thank You !\n\n" + 
				"Team Spartan !!! ";

		emailGenerator(userEmailAddress, userName, subject, msgBody);
	}
	

	public static void sendEmailonCancellation(String userEmailAddress, String userName, String fromLocation, int fromInt, int toInt, int txidInt)  throws UnsupportedEncodingException 
	{

		String subject = "Your booking has been cancelled!";
		String from = fromInt+"";
		String to = toInt+"";
		String txid = txidInt+"";		
		
		String msgBody = "Hello "+ userName + ",\n\nYour booking [Tx Ref :"+ txid +"] from " + fromLocation +
				" for time slot " + from + " to " + to + " has been cancelled on your request. !" +
				"\n\n Thanks for choosing Spartan BikeShare" + 
				"\n\nTeam Spartan !!! ";

		emailGenerator(userEmailAddress, userName, subject, msgBody);
	
	}
	
	public static void emailGenerator(String userEmailAddress, String userName, String subject, String msgBody) throws UnsupportedEncodingException

	{
		Properties props = new Properties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);

		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "true");


		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("bikeshare273@gmail.com", "Spartan BikeShare"));
			msg.addRecipient(Message.RecipientType.TO,
					new InternetAddress(userEmailAddress, "Hello " + userName ));
			msg.setSubject(subject);			
			msg.setText(msgBody);
			Transport.send(msg);

		} catch (AddressException e) {
			System.out.println(e.getMessage());
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
		
		
		
	}


}




