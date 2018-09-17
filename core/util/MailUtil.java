package cn.cloud.core.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class MailUtil {

	private int port = 0;// 端口号
	private String server = "";// smtp服务器
	private String from = "";
	private String user = "";// 发送者地址
	private String password = "";// 密码
	
	public int sendEmail(String email, String subject, String body) {
		int rs = 1;
		try {
			server = Utility.getParamValue("tipMailSmtp");
			port = Integer.valueOf(Utility.getParamValue("tipMailSmtpPort"));
			user = Utility.getParamValue("tipMailAddress");
			password = Utility.getParamValue("tipMailPass");
			
			from = "\""+MimeUtility.encodeText("网吧上网助手")+"\"<"+user+">";
			
			Properties props = new Properties();
			props.put("mail.smtp.host", server);
			props.put("mail.smtp.port", String.valueOf(port));
			props.put("mail.smtp.auth", "true");
			
			Transport transport = null;
			Session session = Session.getDefaultInstance(props, null);
			transport = session.getTransport("smtp");
			transport.connect(server, user, password);
			
			MimeMessage msg = new MimeMessage(session);
			msg.setSentDate(new Date());
			InternetAddress fromAddress = new InternetAddress(from);
			msg.setFrom(fromAddress);
			InternetAddress[] toAddress = new InternetAddress[1];
			toAddress[0] = new InternetAddress(email);
			msg.setRecipients(Message.RecipientType.TO, toAddress);
			msg.setSubject(subject, "UTF-8");
			msg.setContent(body, "text/html;charset=UTF-8");
			msg.saveChanges();
			
			transport.sendMessage(msg, msg.getAllRecipients());
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			rs = -1;
		} catch (MessagingException e) {
			e.printStackTrace();
			rs = -1;
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			rs = -1;
		}finally{
			return rs;
		}
	}

}
