import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailSSL {

	public SendMailSSL(SrReportGenerator parent){
		
		parentSrGenerator = parent;
		try {
			senderMail = parentSrGenerator.getConfigProperty("emailSenderAccount");
			emailPassword = parentSrGenerator.getConfigProperty("emailSenderAccountPassword");
			emailSmtpHost = parentSrGenerator.getConfigProperty("emailSmtpHost");
			emailSmtpPort = parentSrGenerator.getConfigProperty("emailSmtpPort");
			emailAuth = parentSrGenerator.getConfigProperty("emailSmtpAuth");
			
			
			if(senderMail != null && emailPassword != null && emailSmtpHost != null && emailSmtpPort != null && emailAuth != null)
			{	
				props = new Properties();
				props.put("mail.smtp.host", emailSmtpHost);
				props.put("mail.smtp.socketFactory.port", emailSmtpPort);
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", emailAuth);
				props.put("mail.smtp.port", emailSmtpPort);
				
				session = Session.getDefaultInstance(props,
						new javax.mail.Authenticator() {
							protected PasswordAuthentication getPasswordAuthentication() {
								return new PasswordAuthentication(senderMail,emailPassword);
							}
						});
				
				connected = true;
			}
			else
			{
				System.out.println("Invalid config data to connect to email service. Please update config file");
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception connecting to mail server: "+e);
		}

	}
	
	public void sendMail(String textMessage, String receiverMail)
	{
		if(connected)
		{
			try {
				
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("from@no-spam.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(receiverMail));
				message.setSubject("Testing Subject");
				message.setContent(textMessage, "text/html; charset=utf-8");
	
				Transport.send(message);
	
				System.out.println("Done");
	
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		}
		else
		{
			System.out.println("Not connected to email service.");
		}
	}
	private Session session;
	private Properties props;
	private String senderMail, emailPassword, emailSmtpHost, emailSmtpPort, emailAuth;
	private SrReportGenerator parentSrGenerator;
	private boolean connected = false;
}
